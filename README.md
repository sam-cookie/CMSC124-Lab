HiliSaya Programming Language

Creator
Samantha Lodenn D. Lansoy
Chrystie Rae A. Sajorne

Language Overview:
HiliSaya is a  beginner-friendly programming language that is a mix of both Hiligaynon and Bisaya languages. It’s designed for students, teachers, and professionals who speak both languages. It helps programming a lot more understandable and familiar as it uses words that are used most by the target users. 

Main Characteristics: 
-Lower the barrier to learning programming by using local language keywords
-Simple syntax
-Supports the usual building blocks such as variables, functions, classes, basic control flow, and I/O
-Dynamically typed 

Keywords
    Variable (var) : declare a variable 
    Class (klas): define a class 
    Function (func): define a function
    Return (balik): return from a function
    Print (gawas): printing output to console
    If(kung): if condition
    Else(ugdi): else condition
    Elif(kungdi): elif condition
    While(samtang): while loop
    Break(untat): break a loop
    Continue(padayon): continue to next iteration
    True / false (tuod / atik) - Boolean literals
    Null (waay) - no value

Operators:
Arithmetic Operators
    + : Add 
    - : Subtract
    * : Multiply
    / : Divide
    % : Modulo

Comparison Operators:
    > : Greater than
    >= : Greater than or equal to	
    < : Less than 
    <= : Less than or equal to
    = : Equals
    == : Equal to
    != Not equal to

Logical Operators:
    && : logical and
    || : logical or
    ! : logical not

Assignment Operators:
    = : assign
    += : add and assign
    -= : subtract and assign
    *= : multiply and assign
    /= : divide and assign
    %= : module and assign 

Literals:

Number Literals
-Represent the whole numbers or decimals
    Example: 
    42
    -2.1

String Literals
-Enclosed in double quotes (“ ”). Strings can contain letters, numbers, spaces, and symbols
    Example: 
    "Hello, world!"

Boolean Literals
-Represent true values using local words
    tuod = true
    atik = false

Null Literals 
-waay represents no value 

Array Literals 
-Arrays are written using square brackets [] and the values inside it are separated by commas
	Example: 
	[1, 2, 3, 4]

Identifiers:
-Rules for Valid Identifiers
-Identifiers (variable names, function names, etc) must only begin with either a lowercase(a-z) or uppercase letter(A-Z). It can contain numbers or underscores.
-Identifiers cannot use reserved key words
-pecial characters (@,$,#, etc) are not allowed within identifiers, except for underscore(_).
-Identifiers cannot contain whitespace

Comments:
-Line and block comments are supported
    Syntax for line comments : // comment // 
    Syntax for block comments: /// comment ///

Syntax Style:
-Whitespace is not important, except that newlines terminates statements
-Statements end at the end of the line
-Blocks are created using indentation
-Parentheses are used for grouping expressions and function calls 
-Brackets are used for arrays 

Syntax Examples:
Variable declaration
	var age = 20
	var pangalan = “Sir Ren”

Function 
	func greet(name):
		gawas(“hello, ” + name)
	
	greet(“Sir Ren”)

With return value
	func add(a, b) :
		balik a + b
	
	var sum = add(6, 7)
	gawas(sum)

Conditional 
	var age = 20
	kung (edad == 20 ):
		gawas(“Welcome to your 20s!”)
	kungdi (edad >= 30):
		gawas(“Pangasawa na oy”)
	 ugdi:
		gawas(“Bata pa ka chuy”)

Arrays and indexing 
	var lista = [1, 2, 3, 4]
	gawas(lista[0]) // 1

Sample Code:
var minimumAge = 18

func userGreeting(name):
    gawas(“Hello, ” +	name + “!”)
    checkValidity(18)

func checkValidity(userAge):
	kung (age >= 18):
		gawas(“pwede na sa dating app!”)
	ugdi:
		gawas(“hawa diri sa dating app!”)

userGreeting(“Sir Ren”)

Design Rationale:
Familiar vocabulary: using local-language keywords (like gawas and balik) reduces difficulty from understanding  and helps target users learn it faster
Simplicity: small set of keywords to make the language simple and use of newline and indention-based blocks to make programming clean 
Readability: tatements naturally end at the end of a line, and block structures are controlled by indentation, making code easier to read without extra punctuation.
Extensibility: core syntax is minimal so future features (modules, types, standard library) can be added without breaking existing code.
