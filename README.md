Welcome to my replicated version of the river crossing task!
Please view in RAW or code version so UML diagram is actually understandable

If you're not familiar with the paper this was based from:
1. read my review: PaperReview.md
2. access the paper: Robinson, E., Ellis, T., and Channon, A. (2007). Neuroevolution of agents capable of reactive and deliberative behaviours in novel and dynamic environments. In Advances in Artificial Life: Proceedings of the Ninth European Conference on the Synthesis and Simulation of Living Systems (ECAL 2007), pages 345–354.


Here's how the training files are connected:
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
TO TRAIN THE MODEL:
1. 'RcTaskNew' line 105 'Task.Begin(1);' - set the parameter to 0 (replace 1 with 0)
2. 'Environment' line 21 'int EnvoChoice = 2;' - set value to 0 (replace 2 with 0)
3. Run 'RcTaskNew' - this trains the model on the first environment with random initialised weights
4. If program ends before training accuracy reaches over 80% do step 1 but set parameter to 1, then do step 2 again
5. if accuracy >80% 'RcTaskNew' line 105 'Task.Begin(0);' - set the parameter to 1 (replace 0 with 1)
6. 'Environment' line 21 'int EnvoChoice = 0;' - set value to 1 (replace 0 with 1)
7. Run 'RcTaskNew' - this trains the model on the second environment with weights initialised from first environment
8. if accurracy <80%, run 'RcTaskNew' again
9. if accuracy >80%, 'Environment' line 21 'int EnvoChoice = 1;' - set value to 2 (replace 1 with 2)
10. Run 'RcTaskNew' - this trains the model on the third environment with weights initialised from second environment
11. if accurracy <80%, run 'RcTaskNew' again
12. if accuracy >80%, training is complete!!!

TO VIEW ANIMATS IN THE ENVIRONMENT AT ANY POINT:
Run 'VisualEnvironment' - please note this assumes 'RcTaskNew' has been run and 'Weights2' (where weights are stored) has weights of an animat that can do the third environment.

TO CHANGE ENVIRONMENT IN VIEW:
1. 'Environment' line 21 'int EnvoChoice = 2;' - set value to 0,1 or 2 (0=first environment, 1 = second and 2= third environment)
2. Run 'VisualEnvironment'

TO RUN A SPECIFIC ANIMAT:
1. 'VisualEnvironment' line 72-73 comment out 'RCtask.GetEnviro();' and uncomment 'RCtask.ExploreEnviro();'
2. 'VisualAIMoves' line 19 'Task.Weight.WeightAdjust(9);' change parameter for a different animat (change 9 to an int between 0 and 249)
3. Run 'VisualEnvironment'
4. MAKE SURE TO CHANGE STEP 1 BACK!!!!!!!

