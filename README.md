# JavaCAS
A basic computer algebra system made for a math project in class.

There is one class built for the library, that is Functionv2. This contains the ability to create a function from a String,
get a value at a point of that function, get the numerical derivative of a function using (f(x) - f(x+h)) / h,
get the numerical integral of the function using left-hand Riemann sums,
and get the number of extrema of a function by determining where the derivative changes values.

As of now, all basic operators are supported, grouping is supported, the E and PI constants are supported, and an expanding
number of preset functions (I.E. sin(x), cos(x), etc.).
 
The current main class gives you the option to either generate data for the math project or create the function from a text box and use any of the four operations included.

The math project was a task to create a function that would have the largest integral from 1 to 5 out of a list of functions. The limitaiton was that each function cost a certain amount of money and every player only had a certain amount. The program itterates over a certain amount of time and calculates the integrals of a list of randomly generated functions and finds the largest integral. The user has the option to select how much time the program runs for - thus selecting how many functions it generates. The more functions that are generated the more absolute the answer should get. While it is not known how many possibilities there are for a set amount of money, as the amount of time increases, the number of functions generated should approach the limit. 
