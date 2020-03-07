# challenge_2
### Description of the problem and solution.
Task was to render the Wolfram Rule 30 algorithm; achieved by creating a class to carry out the progressiion of simulated cell generations on a timer, and rendering it out in a grid similar 
to how it appears in the wiki.

Using a general MVP architecure to separate concerns, with WolframProgression.kt containing data classes for cells, genreations of cells, and controlling their creation. The View package contains Activities, fragment pagers, fragments, grid adapters to display the data, with an abstract fragment to consolidate boilerplate as subclasses for setting up the individual rendering styles. The Presenter package holds the class that bridges the two, allowing ui to access data.

4 Styles of rendering the data in a viewpager, pause/resume supported when paging around:

- "basic": small generation size, nothing special

- "chonk": larger size, adapter struggles with rendering so many items but wanted to test scale

- "fancy": custom animations for grid cells appearing, effects growing from center out

- "rave": ridiculous color progression

Generation progression executed with RxJava intervals.
