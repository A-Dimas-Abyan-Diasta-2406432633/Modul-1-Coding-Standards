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

## Refleksi (Module 2 - CI/CD)

1. **Code quality issue yang diperbaiki + strategi**  
Saya memperbaiki issue SonarCloud “method kosong” pada `contextLoads()` di `EshopApplicationTests`.  
Strateginya adalah menambahkan assert sederhana (`assertNotNull(applicationContext)`) supaya test benar-benar memverifikasi konteks Spring ter-load, bukan sekadar empty test. Ini membuat test lebih bermakna dan menghilangkan code smell.

2. **Apakah sudah memenuhi CI & CD? (min. 3 kalimat)**  
Menurut saya implementasi ini sudah memenuhi Continuous Integration karena setiap push/PR menjalankan workflow otomatis: build, test, dan analisis kualitas (SonarCloud). Ini memastikan perubahan selalu diuji dan dianalisis sebelum digabung ke branch utama. Implementasi juga memenuhi Continuous Deployment karena setelah merge ke `main`, workflow deploy ke Koyeb berjalan otomatis dan aplikasi ter-deploy tanpa langkah manual tambahan.

## Refleksi (Module 2 - SOLID)

1. Explain what principles you apply to your project.
SRP: Saya pisahkan `CarController` dari `ProductController`, dan memindahkan logika CRUD umum ke `BaseCrudService`, sehingga tiap class punya satu tanggung jawab utama.
OCP: Saya buat `InMemoryCrudRepository<T>` dan `CrudService<T>`, sehingga menambah entity baru cukup membuat model + repository/service turunan tanpa mengubah logic CRUD dasar.
LSP: Saya menghapus inheritance `CarController extends ProductController` karena tidak substitutable; semua controller berdiri sendiri dan tidak melanggar kontrak superclass.
ISP: Saya gunakan interface yang fokus (`CrudService` dan `CrudRepository`) agar class hanya bergantung pada operasi yang relevan.
DIP: Controller bergantung ke `ProductService`/`CarService` (abstraksi), dan service bergantung ke `CrudRepository<T>`, bukan implementasi konkret.

2. Explain the advantages of applying SOLID principles to your project with examples.
Code jadi lebih mudah diperluas dan diuji. Contoh: menambah entity baru bisa memakai `InMemoryCrudRepository<T>` tanpa copy-paste logic CRUD. Controller juga lebih mudah di-mock karena hanya bergantung pada interface, sehingga unit test lebih stabil.

3. Explain the disadvantages of not applying SOLID principles to your project with examples.
Tanpa SOLID, code mudah duplikasi dan coupling tinggi. Contoh: ketika `CarController` mewarisi `ProductController`, `@WebMvcTest` jadi ikut memuat dependency yang tidak relevan dan menyebabkan test gagal. Selain itu, duplikasi CRUD di service/repository membuat perubahan kecil harus diubah di banyak tempat.

## Informasi Deploy & Repositori

- **Deployment URL (Koyeb)**: https://xenogeneic-agnella-dimasabyandad-54ea2aaa.koyeb.app/product/list
- **Repository**: https://github.com/B-Dimas-Abyan-Diasta-2406432633/Modul-1-Coding-Standards
- **Branch**: `module-2-exercise` (merged ke `main`)
