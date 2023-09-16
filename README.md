# HR-*k*ESP

Haiquan Hu, Jifu Chen and Chengying Mao, "HR-kESP: A Heuristic Algorithm for Robustness-oriented k Edge Server Placement", in Proc. of the 23rd International Conference on Algorithms and Architectures for Parallel Processing (ICA3PP'23), October 2023, pp. 1-17, (accepted to appear).

This is the source code and results of HR-*k*ESP algorihtm.

Dataset Source (EUA Datasets): <https://github.com/swinedge/eua-dataset>

File Directory:

./Results

- Comparison on Different Numbers of Base Stations.xlsx
- Comparison on Different Server Budgets.xlsx
- Comparison on Different Numbers of Users.xlsx

./SourceCode

- data/CBD
  - melb_cbd_station.csv
  - melb_cbd_user.csv
- method
  - HRkESP_0.java
  - HRkESP_1.java
  - Main.java
- model
  - BaseStation.java
  - EdgeUser.java
  - Location.java
  - ConstNUm.java
  - Result.java
- utils
  - AlgorithmUtils.java
  - TestUtils.java
