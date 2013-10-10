Hey, welcome to the initial import Ben.

General house keeping, right margin is set to 120 (setting we use) but if this is uncomfortable for mac bros I can
be converted to something smaller (like 90). I tend to avoid going over the margin and use IDEA reformat code
feature when committing which helps keep the code within the margins.

The basic building strategy of the project is
core -> app -> rmtrxdev
Core will build and be exported to app which will build and be exported to rmtrxDev.

To create new activities you must first define them in the AndroidManifest.xml file.
For this project you need to define their full path since we have library modules.
(more on this later)

To do some of your own experiment feel free to create a branch and play around. Just do the following things
git clone git@codeforge.cs.missouri.edu:rmtrx.git (only if you don't already have a copy of the project cloned)
    NOTE: you can't manually download, you need to clone it so your .git folder is created.
git checkout -b test_branch
    NOTE: -b is an argument passed to checkout that indicates that you want a branch created and checked out, the manual
    way to do this is as follows. For more info just do a google search on git checkout or check out git-scm.com
    git branch branchName
    gitch checkout branchName

If you have the extra time I would suggest reading the first 3 chapters of the git book at http://git-scm.com/book but
don't feel like you have to.(note, learning about git helps you understand VCS in general and can be
helpful in interviews)

Next Steps:
Initial Import -- Done
Examples -- TODO: this is next, build some small examples.
Git workflow doc -- TODO: doc that lays out useful git commands to commit and helps streamline the process.
Landing Activity -- TODO: Provide an area to start designing each work flow, this will help everyone get started.
REST client -- TODO: provide a way to make web calls.
DB helper -- TODO: create a global SQLite context possibly, need read/write locks and saftey.