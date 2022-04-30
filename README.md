# MyHeroAcademy
super-heroes library app

Project structure:

Architecture: MVVM - using viewModel and a repository

Tools and libraries: 
LiveData - for configuration changes and observer pattern for ui update.
Retrofit - for api calls.
Dagger - for better dependency injections and instantiations
DiffUtil - improves the performance of the recyclerView
NavigationView - for easier navigation between fragments
Glide - for image loading and cache managment.
Coroutines - for thread managment (works like a threadPool)

The project has 1 activity and 2 fragments for better performance when moving back and forth between screens and it is easier to share data that way.
The first fragment(Search) has an EditText and a recyclerView with thumb (downscaled)images and crops the image to a square,
The recyclerView has 3 favorite heroes that showing despite the search.
the second fragment(Details) loads the image full scale and size
And present information about the chosen super-hero.
All Glide loadings were assigned with a signature that changes once a day that works like expiration date for image cache
** the data presented in the card views of the second screen sets twice because this data was available from the search api but in the home assignment it was told to get it from the dedicated api calls
    So fo performance reasons I display the data that is already available for me from the search but then calling the apis and set the data again.
**
Work in progress:
-make more usage of dagger (maybe even use Hilt)
-move strings to resource folder
-create unit tests
