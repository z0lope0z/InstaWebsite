# InstaWebsite - Android
This app is a tool that integrates with the [Sphere IO](http://www.commercetools.com/en/) PaaS API to help offline stores go online.

1. Scan the product's barcode
2. Take a photo
3. Type in the product's name
4. Upload the product
5. Repeat

Once all offline products are uploaded, use the [Sphere Admin Console](https://admin.sphere.io/) to add the necessary details like price, description, etc..

You can now leverage on Sphere IO.

### TOOLS USED

##### [DM7 Barcode Scanner (Zbar)](https://github.com/dm77/barcodescanner)
Barcode Scanning library

##### [Sphere Android SDK](https://github.com/z0lope0z/sphere-android-sdk)
Incomplete library to integrate with the Sphere IO API using Retrofit and RxJava Observables

##### [Android Priority JobQueue](https://github.com/path/android-priority-jobqueue)
JobQueue to make persistent jobs that run only when the network is available

##### [Butterknife](http://jakewharton.github.io/butterknife/)
View "injection" library to avoid boilerplate, i.e. findViewById()

##### [Dagger 2.0](http://google.github.io/dagger/)
Dependency Injection library to help keep things decoupled

##### [EventBus](https://github.com/greenrobot/EventBus)
The app uses an event bus to notify observers for completed jobs

##### [FloatingActionButton](https://github.com/makovkastar/FloatingActionButton)
Used to display the circular floating button to add new products to the upload list

##### [Realm](http://realm.io/)
New and shiny mobile database. Just wanted to play around with it lol.


