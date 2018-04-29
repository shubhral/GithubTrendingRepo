# GithubTrendingRepo
Shows list of repositories for a particular topic (defaults to android) &amp; opens up their detail screen on tapping any particular repository.

I have implemented MVVM architecture using Android Architecture Components intensively in this app.

# Libraries used - 
1. Android Support Libraries 
2. Retrofit2
3. Dagger2 (android architecture component)
4. Paging (android architecture component)
5. LiveData (android architecture component)
6. ViewModel (android architecture component)
7. OkHttp
8. Timber

# Working

The app has 2 screens. One shows the list of repositories & the other shows the detail of the selected repository.

1. On opening the app, you'd see the first screen showing a list of paginated repositories for topic "Android".
2. On clicking on any repository, you'd see the second screen which shows some repository details.

  	![Alt Text](https://media.giphy.com/media/MX56KhKY0tRedoftuw/giphy.gif)

# TODO

1. Use Data Binding instead of Butterknife
2. Add unit tests
3. Add retry logic
