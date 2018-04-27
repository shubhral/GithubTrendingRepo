# GithubTrendingRepo
Shows list of repositories for a particular topic (defaults to android) &amp; opens up their detail screen on tapping any particular repository.

I have implemented MVVM architecture using Android Architecture Components intensively in this app.

# Libraries used in this project include - 
1. Android Support Libraries 
2. Retrofit2
3. Dagger2
4. Paging
5. LiveData
6. ViewModel
7. OkHttp
8. Timber

# Working

The app has 2 screens. One shows the list of repositories & the other shows the detail of the selected repository.

1. On opening the app, you'd see the first screen showing a list of paginated repositories for topic "Android".
2. On clicking on any repository, you'd see the second screen which shows some repository details.

# TODO

1. Use Data Binding & Butterknife
2. Add unit tests
3. Add retry logic
