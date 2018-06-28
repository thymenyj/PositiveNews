# PositiveNews 
PositiveNews is an news application. The application shows news articles based on the user preferences. The app also filters the negative ners articles out of the newsfeed. 

## Contents
- [Short overview functionalities](#short-overview-functionalities)
- [Challenges](#challenges)
- [Difference from initial proposal](#difference-from-initial-proposal)
- [Possible improvements for the future](#possible-improvements-for-the-future)

## Short overview functionalities
 
  StartScreen:
  - [LoginActivity](#loginactivity):
    *User is able to log into the application.*
  - [RegisterActivity](#registeractivity):
    *User is able to create an account in firebase based on email and password.*
  - [ResetActivity(reset password)](#resetactivity):
    *User is able to reset the password. A password is sent to their email.*
 
  NewsApp:
  - [ProfileFragment](#profilefragment):
    - [ProfileBioTab](#profilebiotab):
      *Shows the name and email of the user.*
    - [ProfileSavedArticlesTab](#profilesavedarticlestab):
      *Shows a list of the saved articles of the user.*
    - [ProfilePreferencesTab](#profilepreferencestab):
      *Shows the current preferences of the user. The user can change the preferences.*
  - [HomeFragment](#homefragment):
    - [HomePersonalTab](#homepersonaltab):
      *Shows a feed of news articles. The articles are selected based on the preferences of the user.*
    - [HomeCategoriesTab](#homepersonaltab):
      *Shows a feed of news articles based on the clicked cateogry button.*
  - [SourcesFragment](#sourcesfragment):
    *Shows a feed of news articles based on the selected news source in the drawer.*
  - [Walkthrough (4 slides)](#walkthroughactivity):
    - Slide 1,2, 4 (text):
      *Shows a welcomes message*
    - Slide 3 (save initial preferences):
      *Shows 6 different cateogries. User is able to set a category to dislike(1), like a bit(50) or like a lot(100).*
    
## Details

### Activities

#### LoginAcitivty
The activity creates a firebase login opportunity for the user. The user is asked for a email and password. The user can also go the the [RegisterActivity](#registeractivity) and [ResetActivity](resetactivity).
In the oncreate a [PositiveWordsRequest](#positivewordsrequest) is called. The positive words are stored in a global hashmap, which can be user in the checkPositivity function.

#### RegisterActivity
The activity creates a firebase register opportunity for the user. The user is asked for a email, password and name. When a registration is 
succesful, a new User is added to the firebase. "firstLogin" is set to true and "preferences" are set to 100.

#### ResetActivity

The activity creates a opportunity for the user to reset their password in case they forgot the password. When a reset is used, a reset is send to the email adress of the user with a link to reset the password.

#### WalkthroughActivity
The activity checks if the user logged in for the first time. When it is the first time the walkthrough is shown, else the user is redirected to the [HomeAcitivty](#homeactivity). The walkthrough contains 4 slides. Slide 1,2 and 4 are slides with text messages. 
Slide 3 shows six pictures of the different categories. The user can click the pictures to change the score of the preferences (1, 50 or 100). The activity updates the slide including the bottom buttons and pagedots.

#### HomeActivity

The activity is a container for three fragments: [ProfileFragment](#profilefragment), [HomeFragment](#homefragment) and [SourceFragment](#sourcefragment).

#### ArticleActivity
The activity shows a webview of the clicked news article. The activity contains two floating action buttons: likeArticle and saveArticle. The likeArticle calls the [ArticleLikeRequest](#articlelikerequest) and updates the score in the firebase with +1. The articleSave calls the [ArticleSaveRequest](#articlesaverequest) and stores the news article in the saved article list in firebase. When the article is already saved, the articleSave button deletes the article from the saved article list.

### Adapters

#### HomeTabAdapter
The adapter sets all the tabs for the [HomeFragment](#homefragment).

#### ProfileTabAdapter
The adapter sets all the tabs for the [ProfileFragment](#profilefragment).

#### WalkthroughSlideAdapter
The adapter sets all the slides for the [WalkthroughActivity](#walkthroughactivity).

### Fragments

#### HomeFragment
The fragment contains two tabs: [HomePersonalTab](#homepersonaltab) and [HomeCategoriesTab](#homecategoriestab).

#### ProfileFragment
The fragment contains three tabs: [ProfileBioTab](#profilebiotab), [ProfileSavedArticlesTab](#profilesavedarticlestab) [ProfilePreferenceTab](#profilepreferencetab).

#### SourcesFragment
The fragment calls [SourcesRequest](#sourcesrequest) to display all the news sources in the drawer on the right. When a news source is clicked, [SourceFeedRequest](#sourcefeedrequest) is called to get the news articles from that news source.

### FragmentTabs

#### HomeCategoriesTab
The tab contains a expandable floating action button with buttons for all the different categories. When one of the buttons is clicked ,[HomeCategoriesRequest](#homecategoriesrequest) is called to get a list of positive articles from that category. The list is casted into the [FeedLayout](#feedlayout).

#### HomePersonalTab
The tab calls [HomePersonalRequest](#homepersonalrequest) to get a list containing positive articles of all the six categories. When the tab received the list with articles, [HomePersonalIndexRequest](#homepersonalindexrequest) is called to get a list of the indexes for the article list. Those indexes are based on the preferences of the user.

#### ProfileBioTab
The tab calls [ProfileBioRequest](#profilebiorequest) to get the name and email of the user and display it in the textviews. The tab also contains a button to log out.

#### ProfileSavedArticlesTab
The tab calls [ProfileSavedArticlesRequest](#profilesavedarticlesrequest) to get a list with all the saved articles of the user.

#### ProfilePreferencesTab
The tab calls [ProfilePreferencesTab](#profilepreferencestab) to get the preferences of the user. The user can push the plus minus buttons to change the score of a category. At the bottom of the page a save button is placed to sumbit the changes to the firebase.

### Layouts

#### FeedLayout
This is the layout used in [HomePersonalTab](#homepersonaltab), [HomeCategoriesTab](#homecategoriestab) and [SourcesFragment](#sourcesfragment).

#### SavedArticlesListLayout
This is the layout used in [ProfileSavedArticlesTab](#profilesavedarticlestab).

#### SourcesLayout
This is the layout used in [SourcesFragment](#sourcesfragment).


### Objects

#### NewsArticles
- String date;
- String time;
- String url;
- String title;
- String categories;
- String image;

#### NewsSource
- String id;
- String name;
- String description;
- String url;

#### Preferences
- float entertainment;
- float health;
- float science;
- float sports;
- float technology;
- float general;

#### User
- String name;
- String email;
- String firstLogin;

### Requests

#### ArticleLikeRequest
The request is called from [ArticleActivity](#articleactivity) and calls the firebase to get the current preferences and updates the score of the category of the article by 1. 

#### ArticleSaveRequest
The request is called from [ArticleActivity](#articleactivity) and calls the firebase to get the current saved articles. When the current article is not in the article list, the news article is added to the list. When the article is already in the list, the news article is deleted from the list.

#### HomeCategoriesRequest
The request is called from [HomeCategoriesTab](#homecategoriestab). The request calls the API to get a list of articles based on the clicked cateogry. The articles are checked on positivity based on the [PositiveWords](#Globals).

#### HomePersonalIndexRequest
The request is called from [HomePersonalTab](#homepersonaltab). The request calls the firebase to get the preferences. Based on the preferences a list of indexes is created. A higher categories score creates more indexes for that cateogry.

#### HomePersonalRequest
The request is called from [HomePersonalTab](#homepersonaltab). The request calls the API to get for all the six categories a list of 100 news articles. It checks the positivity of the articles and combines all the lists to one news article list which is sent back to the [HomePersonalTab](#homepersonaltab).

#### PositiveWordsRequest
The request is called from [LoginActivity](#loginactivity) to get a arraylist of strings with all the positive words. The firebase is called to get all the positive words. The words are stored in the [global](#globals) hashmap.

#### ProfileBioRequest
The request is called from [ProfileBioTab](#profilebiotab). The request calls the firedbase to get the name and email of the user.

#### ProfilePreferencesRequest
The request is called from [ProfilePreferencesTab](#profilepreferencestab). The request calls the firebase to get the preferences of the user. 

#### ProfileSavedArticlesRequest
The request is called from [ProfileSavedArticlesTab](#profilesavedarticlestab). The request calls the firebase to get a list of all the saved articles of the user.

#### SourcesFeedRequest
The request is called from [SourcesFragment](#sourcesfragment). The request calls the API to get news articles based the clicked news source in the drawer.

#### SourcesRequest
The request is called from [SourcesFragment](#sourcesfragment). The request calls the API to get all the news sources to display them in the drawer.

### Globals
Contains a hashmap of the positive words used in [PositiveWordsRequest](#positivewordsrequest).


## Challenges 

In the last weeks several parts were harder to implement than other parts. The implementation of fragments and tab inside the fragments was in the beginning relatively hard, because I had never worked with the fragments (and navigationbars). The fragments were causing a few times minor errors, which were unclear for me, because this was the first time for me I worked with fragment classes. Another hard part was the implementation of the [HomePersonalTab](#homepersonaltab) because of the user preference based news feed. The algorithm did not work in the beginning, because the different listsizes of the individual categories were needed, but also the totallistsize. The callback functions are using variables which are declared in another class. Also there was a problem of duplicated articles in the list, but the NewsArticles were different objects, so I implemented a function which created a new list based on only the titles of the articles to check duplication. Also the positivitycheck was a challenge. I expected that the positivitycheck the hardest part was, but I think this was a bit easier than the preferences. This is maybe due to the fact that I implemented the positivitycheck in the end.

## Differences from initial proposal
The main features are almost the same as the proposal. Although there are some big changes in the design of the app. This is also due to the knowledge of the API in the beginning of the project. I thought I was going to use EventRegistry, but the API allowed a limited of requests. Now I am using NewsApi.org, which gives the opportunity to do more requests. A side effect is that this API does not return a JSON with a "bodyString" containing the text of the article. This is the reason I used a webview for the [ArticleActivity](#articleactivity). Another change in the design of the application is the use of fragments. The fragments were not defined in my design, because I did not know the fragments would be used in my application. Also the different kind of activities differs a bit from my initial idea. The initial idea was that I had one personal feed and one trending. Now it is a personalfeed, which is also trending, and categoryfeed and sourcesfeed. This difference is made, because of the API possibilities. The part that is not implemented is a pop-up in the beginning of the day of a random positive article. 

## Possible improvements for the future
First of all the random popup was not implemented. The idea of the pop-up was to give the user a random positive article, to create a wider perspective of the society for the user. Users often click only on the topics they like (especially in the personal feed). Random articles can diversify the interests of the users. Also the positivity of the articles was below the expectations and could be improved by change the positivityalgorithm. When a body of the text is avaible (in EventRegistry for example) the miminum of positive words needed could be increased, because there is a higher change words are in the text. This creates also more differences between the articles. Also the text could be scraped, but there are a lot of different websites. Another improvement could be checking context of the article. Positive words does not have to mean that the news articles deliver positive content. The meaning of the words can change per context. About the proces, the next time I would start with optimizing the cleanness of the code. I think this will improve the efficiency of the implementation of functions. 


