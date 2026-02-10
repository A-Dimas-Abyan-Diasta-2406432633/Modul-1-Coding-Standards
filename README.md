## Refleksi (Module 1 - Coding Standards)

<details>
  <summary><strong>Refleksi 1 (Clean Code & Secure Coding)</strong></summary>

Setelah mengimplementasikan fitur create dan edit product, saya mengecek kembali source code dan mengevaluasi penerapan coding standards yang dipelajari di modul ini.

### Clean Code Principles yang Sudah Diterapkan

- **Konsistensi Penamaan dan Intention-Revealing**: Saya konsisten menggunakan naming convention yang jelas di semua layer. Method names saya langsung reveal intention seperti `createProductPage` (return view), `createProductPost` (process form), `productListPage` (display list). Variabel juga saya beri nama yang describe content-nya seperti `allProducts` untuk list dan `productIterator` untuk iterator, bukan abbreviation seperti `prod` atau `pList`.
- **Separation of Concerns**: Saya memisahkan aplikasi menjadi layer yang berbeda (Controller, Service, Repository, Model) sesuai Single Responsibility Principle. Controller menghandle HTTP request, Service berisi business logic, Repository mengurus data persistence.
- **Dependency Injection**: Saya pakai `@Autowired` untuk inject dependencies, bukan bikin instance manual. Ini bikin code lebih testable dan ikuti Dependency Inversion Principle.
- **Interface-Based Design**: Saya buat interface `ProductService` dengan implementasi `ProductServiceImpl`, jadi lebih flexible untuk swap implementation atau buat mock object saat testing.

### Secure Coding Practices yang Sudah Diterapkan

- **Output Encoding**: Saya pakai `th:text` di Thymeleaf untuk auto-escape output, mengurangi risiko XSS.
- **UUID untuk ID**: Pakai `UUID.randomUUID()` untuk generate product ID, mencegah enumeration attack dimana attacker bisa tebak ID yang valid.

### Area yang Perlu Diperbaiki

1. **Input Validation yang Lemah**: Belum ada validasi eksplisit untuk input user. Perlu tambah validation annotations seperti `@NotBlank`, `@Min(0)` di model Product untuk ensure data integrity.

2. **Error Handling yang Tidak Lengkap**: Code saya belum handle kasus null atau not found dengan baik. Di `editProductPage`, kalau `findById` return null, aplikasi bisa crash. Harusnya tambah null check atau pakai `Optional<Product>` pattern dan redirect ke error page.

3. **Duplikasi Code di Repository**: Ada logic iterasi yang sama di method `findById` dan `update`. Bisa di-extract jadi private helper method biar ikuti DRY principle.

4. **Thread Safety Issue**: `ProductRepository` pakai `ArrayList` biasa tanpa synchronization. Karena Spring handle concurrent requests by default, ini bisa race condition. Perlu ganti ke thread-safe collection atau tambah synchronization.

5. **Delete Operation Tidak Aman**: Masih pakai GET request untuk delete, yang tidak aman dan melanggar REST best practices. Harusnya pakai POST/DELETE method dengan CSRF protection.

6. **Kurangnya Logging**: Belum ada logging untuk track operasi penting seperti create, update, delete product. Perlu tambah logging pakai SLF4J untuk debugging dan audit.

7. **Direct Model Exposure**: Langsung expose domain model ke controller layer. Lebih baik pakai DTO pattern untuk decouple API layer dari domain layer.

</details>

<details>
  <summary><strong>Refleksi 2 (Unit Test & Code Coverage)</strong></summary>

### 1) Setelah menulis unit test, bagaimana perasaan saya? Berapa banyak unit test yang harus dibuat?

Menulis unit test bikin saya lebih percaya diri sama code yang saya buat. Prosesnya juga bikin saya mikir tentang edge cases dan error scenarios yang tadinya nggak kepikiran. Saya jadi nyadar beberapa masalah di design seperti kurangnya null checks dan error handling yang masih lemah.

Menurut saya nggak ada angka pasti berapa banyak test yang harus dibuat. Tergantung kompleksitas class dan berapa banyak scenario yang perlu di-cover. Aturan umumnya minimal satu test per public method, tapi kalau methodnya complex ya butuh lebih banyak test buat cover berbagai kondisi. Contohnya di `ProductRepositoryTest`, saya bikin beberapa test untuk update dan delete biar cover both success dan failure cases.

Buat mastiin test cukup, saya test semua public methods, boundary conditions, positive/negative cases, dan pakai JaCoCo buat identify untested code. Dari laporan JaCoCo saya dapat **instruction coverage 96.04% (194/202)** dan **branch coverage 75.00% (6/8)**, yang artinya sebagian besar code udah ter-cover tapi masih ada beberapa branch yang belum di-test.

**Code coverage** itu metric yang ukur seberapa banyak code yang di-execute saat test running. Biasanya ada line coverage, branch coverage, sama method coverage. Yang penting dipahami: **100% coverage ≠ no bugs**. Coverage cuma tahu lines mana yang di-execute, bukan apakah test-nya bener verify behavior yang correct. Bisa aja punya 100% coverage tapi assertions-nya salah atau miss important scenarios. Coverage juga nggak detect logic errors, business rule violations, atau performance issues. Jadi saya pakai coverage sebagai guide buat cari gaps, tapi fokus utama tetap di meaningful tests dengan proper assertions.

### 2) Functional test suite baru dengan setup procedures dan instance variables yang sama

Kalau bikin functional test suite baru yang setup-nya sama persis kayak yang udah ada, ini bakal bikin **code duplication** yang melanggar DRY principle. Setiap kali ada perubahan di setup, harus update di banyak tempat. Ribet dan gampang error.

Issue lainnya melanggar **Single Responsibility Principle**. Setiap test class jadi responsible buat test scenarios-nya plus manage setup/teardown sendiri, bikin test classes jadi unnecessarily complex.

**Solusinya:**

1. **Base Test Class**: Bikin abstract class `BaseFunctionalTest` yang contain common setup, instance variables, dan helper methods. Test classes tinggal extend base class ini.

2. **Page Object Pattern**: Represent setiap web page dengan class yang encapsulate page elements dan operations. Bikin tests lebih maintainable dan reduce duplication.

3. **Extract Helper Methods**: Common operations kayak navigate, fill form, verify elements di-extract jadi shared helper methods.

Dengan begini, functional test jadi lebih clean dan easy to maintain. Perubahan setup cukup di satu tempat aja.

</details>