# ShowPictures

another beginner challenge - fetch Pixabay pictures and show them. used Picasso, RecyclerView and StaggeredGridLayoutManager.

TODO:

+ ~~add a refresh button~~
+ ~~pagination~~
+ search query input field
+ automatic column count
+ ~~progress bar when loading~~
+

## How to get a Pixabay API key

Go to [https://pixabay.com/api/docs](https://pixabay.com/api/docs/) and sign up to see your API key in place of "Please login to see your API key here."

Come to the code and change this line,

```java
private static final String pixabayApiKey = System.getenv("FIXABAY_API_KEY");
// into this
private static final String pixabayApiKey = "1234567-abcdefghijklmno0123456789";
```

And do not commit your API key.
