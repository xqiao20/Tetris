# Tetris

## Tetris Game
We developed an Tetris Game and created an AI to play the game automatically. The basic framework of Tetris is based on http://www.neehaw.com/Projects/Item.php?ID=12. The AI algorithm is inspired by http://leeyiyuan.github.io/tetrisai/. 

## Algorithm
### Heuristic Function
a = 0.47015812119690154;  
b = -0.7643351415993872;  
c = 0.3842050185079524;  
d = 0.21709361147244394;  

a is the coefficient of the sum of height of each column.   
b is the coefficient of the full lines.   
c is the coefficient of the holes in the board.   
d is the coefficient of the sum of absolute value of the height difference between two adjacent columns.  

### Scores
Completed full lines*100

### Tips
1. Set images folder as the library root.   
2. Click “File” -> "Invalidate Caches" -> "Invalidate and restart" to make sure no errors when import existing classes.   

