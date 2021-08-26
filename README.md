# Android Samples


## File Sharing
The two apps [file-sharing-producer](https://github.com/TechIsFun/android-samples/tree/main/file-sharing-producer) and [file-sharing-consumer](https://github.com/TechIsFun/android-samples/tree/main/file-sharing-consumer) shows how a _producer_ app can share a file with a _consumer_ app that will update file's content.
Both apps are compatibile with Android 11 file sharing permission system.

## Bracode and QR code scanning
[vision-qr-code-scanner](https://github.com/TechIsFun/android-samples/tree/main/vision-qr-code-scanner) is and app that implements an activity that can read barcodes and QR codes. It's based on [play-services-vision](https://mvnrepository.com/artifact/com.google.android.gms/play-services-vision?repo=google) and it's highly inspired from [this project from googlesamples](https://github.com/googlesamples/android-vision); all dependencies are up-to-date to the latest `androidx` and `play-services-vision` versions.

## Work Manager
[work-manager]](https://github.com/TechIsFun/android-samples/tree/main/work-manager) contains some examples of Work Manager implementations:
- one time worker
- periodic worker
- periodic worker that runs at fixed hours
- chained workers
- a worker with extra dependencies injected by Hilt

## Dependency update
This repository uses [Renovate](https://github.com/renovatebot/renovate) to automatically update dependencies.

## References
- [Setting up file sharing](https://developer.android.com/training/secure-file-sharing/setup-sharing)
- [Sharing a file](https://developer.android.com/training/secure-file-sharing/share-file)
- [FileProvider](https://developer.android.com/reference/androidx/core/content/FileProvider)
