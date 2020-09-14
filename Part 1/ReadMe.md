# Part 1 Notes

## Team Construction
Team Lead:  Jonathan Sullivan  
Design Lead:  Bob Allan  
Test Lead:  Sam Bauter  

## Development Notes  
A separate button to determine BMI seems unnecessary, since the information can simply be displayed on the weight management screen when loaded.  

We're not going to allow rotation of the app, simply because there's no significant benefit provided.  Additionally, Strava and MyFitnessPal don't allow rotation, as precedents.  

"Maintaining weight" will be calculated by simply entering in "0" for the calculator.  

"Gaining weight" doesn't seem to require an actual calculator - put in "0" again, and eat more than that.  

### Identified Bugs
1.  Profile activity is not scrollable.  Corrected by including ScrollView tags.  
2.  Profile male/female buttons are not radioing properly.  Corrected by using orientation in the RadioGroup tag, rather than a LinearLayout inside the RadioGroup.  
3.  Camera is crashing when attempting to launch from the profile activity.  
