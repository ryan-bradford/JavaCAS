# JavaCAS
A basic computer algebra system made for a math project in class.

There is one class built for the library, that is Functionv2. This contains the ability to create a function from a String,
get a value at a point of that function, get the numerical derivative of a function using (f(x) - f(x+h)) / h,
get the numerical integral of the function using left-hand Riemann sums,
and get the number of extrema of a function by determining where the derivative changes values.

As of now, all basic operators are supported, grouping is supported, the E and PI constants are supported, and an expanding
number of preset functions (I.E. sin(x), cos(x), etc.).
 
The current main class gives you the option to create the function from a text box and use any of the four operations included.
