

# ATOM Business Policy Component SDK demo for Android

This is a demo application that demonstrate how to use **BPC SDK** along with **ATOM SDK** 

BPC SDK provides the customizable inventory which enables you to offer different sets of entities to your end-users with the help of customized *Packages* and *Groups*. BPC SDK will also provide *Custom Attributes* that you can associate with every byte of system related data e.g. Countries data is Atom's property but through BPC, you can add Custom Attributes to Country's object like flag icon etc which enables to stay back-end-free and BPC will serve as your customized back-end.

# BPC Features explained in this Demo
* How to get Inventory filtered by Packages
* Some frequently used methods present in the SDK

 ## Compatibility
* Compatible with Android 4.0/API Level: 14 (ICE_CREAM_SANDWICH) and later
* Compatible with ATOM SDK Version 2.3.0 and onwards

Add this to root **build.gradle** 

# Setup
To use this library you should add **jitpack** repository.
Add **authToken=jp_l1hv3212tltdau845qago2l4e** in gradle.properties of your root project

Add this to root **build.gradle** 
```gradle
    allprojects {
        repositories {
            maven { url 'https://jitpack.io'
                credentials { username authToken }
            }
        }
    }
```

And then add dependencies in **build.gradle** of your app module.
```gradle
dependencies {
    implementation 'org.bitbucket.purevpn:android-bpc-sdk:{latest_version}'
}
```

### compatibility with java 1.8
```gradle
 compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
```
>To successfully build ATOM SDK, developer must migrate their project to AndroidX. Developer can use **Refactor** -> **Migrate to AndroidX** option in Android Studio.

>Developer must enable Kotlin support in Android Studio using Kotlin Extension.

### Setup Kotlin Extension in Android Studio

Add Kotlin gradle plugin to project build.gradle
```
classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.31"
```

Add Kotlin Android Extension plugin to app build.gradle
```
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
```
Add Kotlin support to app build.gradle in dependencies
```
implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.31"
```
# Getting Started with the Code
## Initialization
BPC SDK needs to be initialized with a “SecretKey” provided to you after you buy the subscription which is typically a hex-numeric literal.

``` Kotlin
val atomConfiguration = AtomConfiguration.Builder(
            "YOUR_SECRET_KEY"
        ).build()
        
       val atomBpcManager = AtomBPCManager.initialize(atomConfiguration)
```

# How to get Inventory related to customer's Package
BPC enables you to define and sell your customers your own choice of inventory by creating packages. Through BPC SDK, you can get complete inventory as well as get it filtered by your logged in customer's package. Following are some code examples to achieve the same: 


### Get All packages
Call this method to get all packages from your inventory 
```Kotlin
bpcManager?.getPackages({packages->
            //here you get the all the packages
        }, {
        // here you will get the exception
        })
```



### Get Countries filtered by Package
This function will retrieve all countries that are associated with a particular package 
```Kotlin
bpcManager?.getCountriesByPackage(PackageObject, {
    // here you will get all the countries that are associated with this packages
},{
   // here you will get the exception
})
```

### Get Protocols filtered by Package
This function will retrieve all protocols that are associated with a particular package 

```Kotlin
bpcManager?.getProtocolsByPackage(PackageObject,{
    // here you will get the list of protocols that are associated with this package
},{
    // here you will get the exception
})
```


# Some other functions that are helpful to retrieve common inventory items 

### Get all Countries
This function will provide the list of all countries present in your inventory
``` Kotlin
bpcManager?.getCountries({countries->
            //here you get the all the packages
        }, {
        // here you will get the exception
        })
```


### Get Countries filtered by Protocol
This function will provide you the list of countries that are mapped with a specific protocol
```kotlin
bpcManager?.getCountriesByProtocol(protocolObject, {
               //here you ill get the list of countires that supports provided protocol

            }, {
                        // here you will get the exception
            })
```


### Get all Cities
This function will provide the list of all cities present in your inventory
```kotlin
bpcManager?.getCities({
               //here you get the list of cities from the whole inventory 

            }, {
                        // here you will get the exception
            })
```

### Get cities by protocol
This function will provide you the list of cities that are mapped with a specific protocol
```kotlin
bpcManager?.getCitiesByProtocol(protocol, {
               //here you get the list of cities that supported provided protocol

            }, {
                        // here you will get the exception
            })
```
