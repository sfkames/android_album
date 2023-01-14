# android_album
Android app for single-user photo application that allows storage and management of photos in one or more albums

CS213 Assignment 4
Samantha Ames and Ahmed Elgazzar

Photos
You are not required to carry over all the functionality from the Java FX implementation. Instead, you will implement a smaller set, as described below. You may reuse any of the code from your Java FX project.

Since the app will run on a personal smart phone, there is only a single user, who is the owner of the phone. So there is no logging in, and no admin functionality. Also, explicit captions are not required for photos (the filename will stand for the caption). Dates are not required either.

Your app should implement the following features:
30 pts Home screen. When the app comes up, it should load album and photo data from the previous session, if any, and list all albums with names in plain text. Off this "home" screen, you should be able to do the following, in one or more navigational steps.
40 pts Open, create, delete, and rename albums as listed in the Java FX project description. When opening an album, display all its photos, with their thumbnail images.
40 pts Once an album is open, you should be able to add, remove, or display a photo. The photo display screen should include an option for a slideshow, allowing you to go backward or forward in the album one photo at a time with manual controls.
20 pts When a photo is displayed, you should be able to add a tag to a photo. Only person and location are valid tag types; there are no typeless tags. You should also be able to delete a tag from a photo. Note: When displaying a photo, tags (if any) should be visible.
20 pts You should be able to move a photo from one album to another
50 pts You should be able to search for photos by tag-value pairs, just like in the Photos assignment (of course now limited to only person and location type tags). All matches are case insensitive, so "new york" is the same as "nEw YOrk".
Moreover, matches should allow auto completion, given a starting substring. For instance, when searching by location, if "New" is typed, matches should include photos taken in New York, New Mexico, New Zealand, etc. In other words, all locations that start with the typed text. The user can then pick from this auto-completed list.

Searches apply to photos across all albums, not just to the album that may be open.

Note: You are NOT required to implement functionality to add new tag types, or save the matching photos in an album.

You may save photos to the device's gallery.
