I would rate my AI use in this assignment as a 3-4 because there was good portions of the main methods and test suites that needed assistance, but the first 3 definitions and this file were written with little to no assistance. I used Claude as my assistant. 
For this assignment, the timing method I used was median nanoseconds per call. 

Timing cases for required inputs:

case           definition   cancellation      recursive    agree       def ns      canc ns       rec ns   reps
C( 5, 2)               10             10             16       NO           61           21          130  20000
C(10, 3)              120            120            176       NO           21           16          619  20000
C(15, 7)             6435           6435          16384       NO           28           12        42788   1554
C(20,10)           184756         184756         616666       NO           21           13      2372275     54

Boundary and interior cases:

case           definition   cancellation      recursive    agree      reference
C( 0, 0)                1              1              1      yes              1
C( 1, 0)                1              1              1      yes              1
C( 1, 1)                1              1              2       NO              1
C( 5, 0)                1              1              1      yes              1
C( 5, 5)                1              1             32       NO              1
C(20, 0)                1              1              1      yes              1
C(20, 1)               20             20             21       NO             20
C(20,19)               20             20        1048575       NO             20
C(20,20)                1              1        1048576       NO              1
C(17, 8)            24310          24310          65536       NO          24310

Invald inputs:

case             definition   cancellation      recursive   all -1
C( -1,  0)              -1             -1             -1      yes
C(  5, -2)              -1             -1             -1      yes
C(  3,  5)              -1             -1             -1      yes
C( -4, -4)              -1             -1             -1      yes
C(  0,  1)              -1             -1             -1      yes
C( -7,  3)              -1             -1             -1      yes

Recursive slowdown: 

case         true value      rec calls     def ns    canc ns         rec ns   reps   def  canc  rec
C(10, 5)            252            503         22         21           2786  20000    ok    ok  BAD
C(14, 7)           3432           6863         25         20          38714   2913    ok    ok  BAD
C(18, 9)          48620          97239         17         13         785939    205    ok    ok  BAD
C(22,11)         705432        1410863         21         14        9451380     14   BAD    ok  BAD
C(26,13)       10400600       20801199         41         17      144788772      1   BAD    ok  BAD
C(28,14)       40116600       80233199         28         18      610194562      1   BAD    ok  BAD
C(30,15)      155117520      310235039         46         19     2465288803      1   BAD   BAD  BAD

Overflow:

case                          reference   fits             definition      ok?           cancellation      ok?
C( 20, 10)                      184756    yes                 184756       OK                 184756       OK
C( 21,  1)                          21    yes                     -1    WRONG                     21       OK
C( 21, 10)                      352716    yes                 -29335    WRONG                 352716       OK
C( 25, 12)                     5200300    yes                      2    WRONG                5200300       OK
C( 30, 15)                   155117520    yes                      9    WRONG                 -54279    WRONG
C( 40, 20)                137846528820    yes                      0    WRONG                     -1    WRONG
C( 50, 25)             126410606437752    yes                      0    WRONG                      0    WRONG
C( 60, 20)            4191844505805495    yes                      1    WRONG                      0    WRONG
C( 62, 31)          465428353255261088    yes                      0    WRONG                      0    WRONG
C( 65,  3)                       43680    yes                     -1    WRONG                  43680       OK
C( 66,  0)                           1    yes    ArithmeticException    WRONG                      1       OK
C( 66, 33)         7219428434016265740    yes                      0    WRONG                     -1    WRONG
C( 70, 35)       112186277816662845432     no    ArithmeticException    WRONG                      0    WRONG
C(100, 50)  100891344545564193334812497256     no    ArithmeticException    WRONG                      0    WRONG
C(132, 66)  377389666165540953244592352291892721700     no    ArithmeticException    WRONG    ArithmeticException    WRONG

Overall, the efficiency depends on the method itself. The two loop methods, definition and cancellation, are linear in their results. The cost of the recursive method however is directly proportional to the result, meaning that bigger results will have a bigger cost. 

One of the overflow related failures that occured in the program was when executing binomialDefinition(66,0). It says its ok when it shouldn't have since the factors of 2 in 66! = 64, and the denominator becomes 0 which throws a division by 0 exception. 

