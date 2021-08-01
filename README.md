# 🚀 Merhaba vantalii!

#### Front-End
CSS, JS, VUE dosyalarını aşağıdaki klasörlerde bulunduruyoruz.

>- /resources/static/styles/*.scss
>- /resources/static/js/*.js
>- /resources/static/vue/*.vue

ilk kurulumda ya da herhangi bir dependency güncellemesi sonrası

```
npm install
```

daha sonrası tüm js, css, vue güncellemelerinde 

```
npm run build
```

kullanılacaktır. Geliştirme yaparken hot development özelliği vardır, komut çalıştırdıktan sonra aktif olduğu sürece değişiklikler otomatik olarak güncellenecektir.

İşlenen dosyalar aşağıdaki klasörde barındırılacaktır. 
 
> /resources/static/bundle/**


#### Deploy

```
clean package -DskipTests -X -e -Pprod
```