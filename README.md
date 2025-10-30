Welcome to my replicated version of the river crossing task!
Here's how the files are connected:
                        |RcTaskNew|
          /          /       |       \           \
R Composes U       /         |      R Composes N    R Composes W
   /  U associates with R    |          \                   \
  /            /        R composes P  |NormalPass|        |Weights|
|UpdateWeights|              |          /                   /
                             |    N Depends on P     W depends on P
                             |    /                   /
                           |Pass|
                         /        \ 
            P composes D          P composes S
              /                        \
  |DecisionNetwork|                  |ShuntingModel|
                                          |
                                      S composes E
                                          |
                                     |Environment|
