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
The activity creates a firebase login opportunity for the user. The user is asked for a email and password. The user can also go the the RegisterActivity and ResetActivity.
In the oncreate a PositiveWordsRequest is called. The positive words are stored in a global hashmap, which can be user
in the checkPositivity function.

#### RegisterActivity
The activity creates a firebase register opportunity for the user. The user is asked for a email, password and name. When a registration is 
succesful, a new User is added to the firebase. "firstLogin" is set to true and "preferences" are set to 100.

#### ResetActivity

The activity creates a opportunity for the user to reset their password in case they forgot the password. When a reset is used, a reset is send to the email adress of the user with a link to reset the password.

#### WalkthroughActivity
The activity checks if the user logged in for the first time. When it is the first time the walkthrough is shown, else the user is redirected to the [HomeAcitivty](#HomeActivity). The walkthrough contains 4 slides. Slide 1,2 and 4 are slides with text messages. 
Slide 3 shows six pictures of the different categories. The user can click the pictures to change the score of the preferences (1, 50 or 100). The activity updates the slide including the bottom buttons and pagedots.

#### HomeActivity

The activity is a container for three fragments: [ProfileFragment](#ProfileFragment), [HomeFragment](#HomeFragment) and [SourceFragment](####SourceFragment).

#### ArticleActivity

### Adapters

#### HomeTabAdapter

#### ProfileTabAdapter

#### WalkthroughSlideAdapter

### Fragments

#### HomeFragment

#### ProfileFragment

#### SourcesFragment

### FragmentTabs

#### HomeCategoriesTab

#### HomePersonalTab

#### ProfileBioTab

#### ProfilePreferencesTab

#### ProfileSavedArticlesTab

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


  
