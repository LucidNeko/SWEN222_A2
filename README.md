Wolf3D
==========

Key concepts:

  The X axis is left:-1 and right:1
  The Y axis is bottom:-1 and top:1
  The Z axis is far:-1 and near:1 (i.e z points out of the screen at you)
  
  The Component based design idea relies on making well formed 
    highly encapsulated components that just do their specific task.
  
  i.e. Instead of making a 'Player' class - You instantiate an Entity 
    (an empty shell that holds components) and then add all the functionality
    you need to this Entity in the form of Components. As opposed to bunging
    all that functionality haphazardly into a 'Player' spaghetti class.

Entry into the program:

App contains a main method which instantiates itself:
 - Creating a JFrame (itself).
 - Registers the Input classes Mouse and Keyboard.
 - Creates a new World.
 - Adds a test entity to the world.
 - Creates the WorldView - passing in the World.
 - packs and sets itself to be visible.
 - Creates a GameDemo - passing in the World.
 - Sets the View of the GameDemo to be the WorldView we created before.
 - Starts the game running.

GameLoop is a thread that can be extended allowing you to create a class such as GameDemo easily.
