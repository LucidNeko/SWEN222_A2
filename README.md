Wolf3D
==========
This is a start for the class diagram

<!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=5,IE=9" ><![endif]--><!DOCTYPE html><html>
<head>
<title>Class Diagram</title>
</head>
<body>
<div class="mxgraph" style="position:relative;overflow:hidden;width:821px;height:572px;">
<div style="width:1px;height:1px;overflow:hidden;">7ZnPb+I6EMf/GqTdQ1dAKNBjKbQ9vJWeVOl192iICVadOOuYX/3rO7bHSUxgy1toFHW5QPz1eDyMM5/YpBXcxZsHSdLFdxFS3uq2w00rGLe63c5wOIAvrWytMhz0rRBJFqJRITyxV4piG9UlC2nmGSohuGKpL85EktCZ8rS54P4UKYmc+0J4mhFeVZ9ZqBYYchdD1vojZdHCTdPp39ieTG2dj5DOyZKrKyNBn+6OifOFv2rTts0Bjt9iu9NGISWJF9KrELEnSJoVucKfyzAunGQqZEillazCWfJSTlswgaWTQsA4fRVv7ijXy+dWxjq6P9CbZ0vSxJv50ACMYkX4EiNvdfscho5SL4f9X0sd0igmMmKQh1vobacb+ATRpFPrV0qktq9X6lN0o64IZxGOm0FskITcJ1xF+G1mnpYEEoPDkZH9FplmShK4u5zqj/Acws8v+6xMYgUd0bvDQIPE+FoDMvVAYvqPAJd1hM7pHKzy2A8Gty+hlQgWFauPiEmx2cuXr3VnAooQqv3QvDuQWlEJURJ+a5d/bG6QEd4MYzvTSIDVnIs1KHMGhRyM5iJRiOgO0MO070nMuIb7I+Urqr3qRKsY7Md7CIGSDoC6h0SOIniKUBFTJbeakQiNIeIMHyAd92RYF4Tu9FFbeHRGkSDyotx3wSi4QEztR1bQZGTpQrwU4WkxfZ7C6N4cWRhu3Cl10atkjoawlcKmkGohIpEQPinUkRRLQJR2oFcyU0SqWylNGqdczF6ceG9yaoyAac4kEYl2AkrefyiHmVjKGYaFew7wG1G0wprWAf82z5JyotjK32SdkrTrJsPkP0bXF5jsjylkWcrJ9vM9XHcQEtSJEDxUnQshyIfzEwRxUSYIFvL5CGKGQpxEj3IGqWCJykqe/9VCsXj5yrjVC7zjF1xYj8Vq5KEdtUB4cm8mrp6FhMP9hVd7Y4IbdZIophjNPh+zeru3fZ3QGjYAWtDaMPXDpNNe/9T6N6TS/8cZFnr9GyL8s6uZhDEVBD/ngpi/83zV39kc9fDxWgtn3H8Xjd8dITvKOMGqrh8nHbfGjeTJnYhTyLHZ0l2Ysi8msU5MJpuGkQoz9lTF0Ri5dsioBSPVVy4L8HcqWo7Axk71v8sRhEaZI7ac6+BG9V/e82QJ4si3abrh9mkfkkFHvwqK6z6XDnY26K4C8teCO/ZDt4H/Q/ve4KRzLzSLV6DWvHiVHUzeAA==</div>
</div>
<script type="text/javascript" src="https://www.draw.io/embed.js"></script>
</body>
</html>

If you want to edit the file use this link
https://drive.google.com/file/d/0B2CawmmmcfjScTc3aTQ2LWo0c1k/view?usp=sharing
Note: Need system to generate unique IDs for Entities.

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

wolf3d.Wolf3D contains a main method which instantiates itself:
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
