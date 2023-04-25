# Wordpress Mobile App

The App is using the [Dictionary Api] to translate word.

### Build With 🏗️
- [Kotlin] - Programming language for Android
- [Hilt-Dagger] - Standard library to incorporate Dagger dependency injection into an Android application.
- [Retrofit] -  A type-safe HTTP client for Android and Java.
- [Room] - SQLite object mapping library.
- [Coroutines] - For asynchronous
- [LiveData] - Data objects that notify views when the underlying database changes.
- [ViewModel] - Stores UI-related data that isn't destroyed on UI changes.
- [ViewBinding] - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
- [Jetpack Navigation] - Navigation refers to the interactions that allow users to navigate across, into, and back out from the different pieces of content within your app
   
   [ViewModel]: <https://developer.android.com/topic/libraries/architecture/viewmodel>  
   [Jetpack Navigation]: <https://developer.android.com/guide/navigation/>  
   [Hilt-Dagger]: <https://dagger.dev/hilt/>  
   [DataStore]: <https://developer.android.com/topic/libraries/architecture/datastore>
   [ViewBinding]: <https://developer.android.com/topic/libraries/view-binding>
   [LiveData]: <https://developer.android.com/topic/libraries/architecture/livedata/>
   [Retrofit]: <https://square.github.io/retrofit/>
   [ViewModel]: <https://developer.android.com/topic/libraries/architecture/viewmodel>
   [Kotlin]: <https://kotlinlang.org>
   [Coroutines]: <https://kotlinlang.org/docs/coroutines-overview.html>
   [MVVM (Model View View-Model)]: <https://developer.android.com/jetpack/guide#recommended-app-arch>
   [Dictionary Api]: <https://api.dictionaryapi.dev/>
   [Room]: <https://developer.android.com/training/data-storage/room/>
   
### Project Architecture 🗼

This app uses [MVVM (Model View View-Model)] architecture.


### Project Run
- You need these files to run the project or you can create it yourself for testing

- gelecekbilimde-config.properties
- BASE_URL="https://ferhatozcelik.com/";
- YOUTUBE_API="https://www.googleapis.com/youtube";
- YOUTUBE_API_KEY="";
- CHANNEL_ID="UC03cpKIZShIWoSBhfVE5bog";
- PLAYLIST_ID="UU03cpKIZShIWoSBhfVE5bog";

- google-services.json
- Firebase>Application Setting > google-services.json File Download


###--> START - GENERAL CONFIG

APPLICATION_ID=com.ferhatozcelik.wordpress
APPLICATION_NAME=Ferhat OZCELIK
APPLICATION_DESCRIPTION=Android Developer and Physicist
BASE_URL=https://ferhatozcelik.com/

### --> END - GENERAL CONFIG

### --> START - YOUTUBE VIDEOS CONFIG
IS_YOUTUBE=false
YOUTUBE_API=https://www.googleapis.com/youtube
YOUTUBE_API_KEY=
CHANNEL_ID=UC...
PLAYLIST_ID=UU...
### --> END - YOUTUBE VIDEOS CONFIG


### --> START - YOUTUBE VIDEOS CONFIG visible 0 , invisible 1, gone 2

BLOG_INFO_STATUS=true
BLOG_INFO_TITLE=null
BLOG_INFO=null

DONATION_STATUS=2
DONATION_URL=null

TWITCH_STATUS=2
TWITCH_URL=null

YOUTUBE_STATUS=0
YOUTUBE_URL=https://youtube.com/ferhatozcelik

TWITTER_STATUS=0
TWITTER_URL=https://www.twitter.com/ferhatozcelik

INSTAGRAM_STATUS=0
INSTAGRAM_URL=https://www.instagram.com/ferhatozcelik0

SPOTIFY_STATUS=2
SPOTIFY_URL=null

### --> END - YOUTUBE VIDEOS CONFIG

### --> START - BUILD CONFIG
storePassword=
keyAlias=
keyPassword=
### --> END - BUILD CONFIG


