# Draw It or Lose It

  The Gaming Room is a company that developed a game called Draw It or Lose it. They need consultation on turning their game 
into a a web-based version of the gaming app. The Gaming Room require one instance to control the environment and not allow any other 
instances to be created. Draw It or Lose It requires storage and retrieval of several hundred stock images and scalability 
for their game as a RESTful api. 

This document does a good job of designing and implementing the singleton-pattern to control all instances of Games, teams, 
and players. 

The structure, and requirements in the design document greatly helped when designing the code and guided specifics in the
the implementation of a RESTful api and singleton pattern. 

The design constraints in this document do not adequately cover security measures. This would improve the thoroughness and 
design considerations evident throughout the document.

When thinking about the requirements for this software design I put on the end user's shoes to see what would make the 
application as appealing and familiar as the mobile applicationg The Gaming Room has already produced. It is very important
to design software in a manner that caters to the user. If the user doesn't like the application then they won't use it. 
Applications are only as good as they are enjoyable and effective.

Psuedo code, decision tables, and both top-down and bottom-up design helps in the overall perspective of software design. 
These techniques along with thorough limit testing will play a large roll in the future development of software applications. 
