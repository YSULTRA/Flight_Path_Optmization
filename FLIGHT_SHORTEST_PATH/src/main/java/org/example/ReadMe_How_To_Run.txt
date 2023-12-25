Following libraries need to be installed in the testing system

        pip install pandas
        pip install networkx
        pip install matplotlib

        Java: Ensure that you have Java installed on your system. You can download it from OpenJDK.

        Python: Make sure you have Python installed on your system.

        Ensure that both Java and Python are added to your system's PATH for successful execution.

Step 1:

       Extract the downloaded zip file.
       Open the extracted folder using IntelliJ IDEA.

Step 2:

       Navigate to target/FLIGHT_SHORTEST_PATH-1.0-SNAPSHOT.
       Run the JAR file through IntelliJ IDEA.
       CLick on the JAR file, click on the option RUN "FLIGHT_SHORTEST_PATH-1.0-SNAPSHOT" through Intellij


Step 3:

       For Airport Names Consider List airport_list = ['CDG', 'HKG', 'LAX', 'DXB', 'LHR', 'JFK', 'NRT', 'SYD', 'SFO', 'SIN']
       Follow the prompts to enter the source and destination airports.
       The Java code will generate an output file (output.txt) containing the shortest path and total weight.

The Java code will execute the Python script (OutputGraph.py) to visualize the flight path.

Note: Ensure that the Python script uses the correct path for your Python installation.

The graph visualization will be displayed, showcasing the shortest path between the selected airports.

The CSV file (flights_data_NEW.csv) contains flight data with columns: SOURCE, DESTINATION, and WEIGHT.

The Python script uses the pandas, networkx, and matplotlib libraries for graph visualization.