# PositiveNews 
PositiveNews is an news application. The application shows news articles based on the user preferences. The app also filters the negative ners articles out of the newsfeed. 

## Short overview
 
  StartScreen:
  - LoginActivity:
    *User is able to log into the application.*
  - RegisterActivity:
    *User is able to create an account in firebase based on email and password.*
  - ResetActivity (reset password):
    *User is able to reset the password. A password is sent to their email.*
 
  NewsApp:
  - ProfileFragment:
    - ProfileBioTab:
      *Shows the name and email of the user.*
    - ProfileSavedArticlesTab:
      *Shows a list of the saved articles of the user.*
    - ProfilePreferencesTab:
      *Shows the current preferences of the user. The user can change the preferences.*
  - HomeFragment:
    - HomePersonalTab:
      *Shows a feed of news articles. The articles are selected based on the preferences of the user.*
    - HomeCategoriesTab:
      *Shows a feed of news articles based on the clicked cateogry button.*
  - SourcesFragment:
    *Shows a feed of news articles based on the selected news source in the drawer.*
  - Walkthrough (4 slides):
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
The activity shows a webview of the clicked news article. The activity contains two floating action buttons: likeArticle and saveArticle. The likeArticle calls the [ArticleLikeRequest](#articlelikerequest) and updates the score in the firebase with +1. The articleSave calls the [ArticleSaveRequest](#articlesaverequest) and stores the news article in the saved article list in firebase.

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
The tab calls [ProfileBioRequest](#profilebiorequest) to get the name and email of the user and display it in the textviews. The button also contains 

#### ProfileSavedArticlesTab

#### ProfilePreferencesTab


### Layouts

#### FeedLayout

#### SavedArticlesListLayout

#### SourcesLayout


### Objects

#### NewsArticles

#### NewsSource

#### Preferences

#### User


### Requests

#### ArticleLikeRequest

#### ArticleSaveRequest

#### HomeCategoriesRequest

#### HomePersonalIndexRequest

#### HomePersonalRequest

#### PositiveWordsRequest

#### ProfileBioRequest

#### ProfilePreferencesRequest

#### ProfileSavedArtilesRequest

#### SourcesFeedRequest

#### SourcesRequest

### Globals


  
