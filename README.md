# tankgame

A split screen two player tank-style game written in Java. 

Each player has separate controls, can shoot projectiles, and three lives.
The game map contains several different types of power-up objects, damage inflicting obstacles, breakable walls, and a fixed mini-map.


In addition to projectiles fired by the opposing player, damage can be inflicted by the moving objects of the opposite type. The turtle player will be damaged by the plastic ring moving objects, and will not be damaged by the recycling moving objects. The plastic bag player will be damaged by the recycling moving objects and will be unharmed by the plastic ring moving objects.

Power-up objects located throughout the map:
- Shields: will project the player from damage one time
- Star: will change all damage inflicting moving object to the opposite type of the player who got the star object. 
