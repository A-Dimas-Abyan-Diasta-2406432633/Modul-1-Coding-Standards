## Refleksi (Module 1 - Coding Standards)

<details>
  <summary><strong>Refleksi 1 (Clean Code & Secure Coding)</strong></summary>

Setelah mengimplementasikan fitur create dan edit product, saya mengecek kembali source code dan mengevaluasi penerapan coding standards yang dipelajari di modul ini.

### Clean Code Principles yang Sudah Diterapkan

- **Konsistensi Penamaan**: Method names saya langsung reveal intention seperti `createProductPage` (return view), `createProductPost` (process form). Variabel juga descriptive seperti `allProducts` untuk list, bukan abbreviation kayak `prod`.
- **Separation of Concerns**: Saya pisahkan aplikasi jadi layer berbeda (Controller, Service, Repository, Model) sesuai Single Responsibility Principle.
- **Dependency Injection**: Pakai `@Autowired` untuk inject dependencies, bukan bikin instance manual. Ini bikin code lebih testable.
- **Interface-Based Design**: Buat interface `ProductService` dengan implementasi `ProductServiceImpl`, jadi flexible untuk swap implementation atau mock saat testing.

### Secure Coding Practices yang Sudah Diterapkan

- **Output Encoding**: Pakai `th:text` di Thymeleaf untuk auto-escape output, mengurangi risiko XSS.
- **UUID untuk ID**: Pakai `UUID.randomUUID()` untuk generate product ID, mencegah enumeration attack.

### Area yang Perlu Diperbaiki

1. **Input Validation yang Lemah**: Belum ada validasi eksplisit. Perlu tambah annotations seperti `@NotBlank`, `@Min(0)` di model Product.

2. **Error Handling Kurang**: Di `editProductPage`, kalau `findById` return null, aplikasi bisa crash. Harusnya tambah null check atau pakai `Optional<Product>`.

3. **Code Duplication**: Ada logic iterasi yang sama di `findById` dan `update`. Bisa di-extract jadi private helper method.

4. **Thread Safety**: `ProductRepository` pakai `ArrayList` tanpa synchronization, bisa race condition saat concurrent requests.

5. **Delete Pakai GET**: Masih pakai GET request untuk delete, harusnya POST/DELETE dengan CSRF protection.

6. **Kurang Logging**: Belum ada logging untuk track operasi penting. Perlu tambah SLF4J untuk debugging dan audit.

7. **Direct Model Exposure**: Langsung expose domain model ke controller. Lebih baik pakai DTO pattern.

</details>

<details>
  <summary><strong>Refleksi 2 (Unit Test & Code Coverage)</strong></summary>

### 1) Setelah menulis unit test, bagaimana perasaan saya? Berapa banyak unit test yang harus dibuat?

Menulis unit test bikin saya lebih percaya diri sama code dan mikir edge cases yang tadinya nggak kepikiran. Nggak ada angka pasti berapa banyak test yang harus dibuat, tergantung kompleksitas class. Minimal satu test per public method, tapi kalau complex butuh lebih banyak untuk cover berbagai kondisi.

Buat mastiin test cukup, saya test semua public methods, boundary conditions, positive/negative cases, dan pakai JaCoCo. Dari laporan JaCoCo saya dapat **instruction coverage 96.04% (194/202)** dan **branch coverage 75.00% (6/8)**.

**Code coverage** ukur seberapa banyak code yang di-execute saat test running. Yang penting: **100% coverage ≠ no bugs**. Coverage cuma tahu lines mana yang di-execute, bukan apakah test verify behavior yang correct. Saya pakai coverage sebagai guide buat cari gaps, tapi fokus di meaningful tests dengan proper assertions.

### 2) Functional test suite baru dengan setup procedures yang sama

Kalau bikin functional test suite baru yang setup-nya sama persis, ini bikin **code duplication** yang melanggar DRY principle. Setiap perubahan di setup harus update di banyak tempat, ribet dan gampang error. Issue lainnya melanggar **Single Responsibility Principle** karena test class jadi responsible buat test scenarios plus manage setup/teardown sendiri.

**Solusinya:**

1. **Base Test Class**: Bikin abstract class `BaseFunctionalTest` yang contain common setup dan helper methods. Test classes tinggal extend.

2. **Page Object Pattern**: Represent setiap web page dengan class yang encapsulate page elements dan operations.

3. **Extract Helper Methods**: Common operations di-extract jadi shared helper methods.

Dengan begini, functional test jadi clean dan perubahan setup cukup di satu tempat.

</details>