import pandas as pd
import networkx as nx
import matplotlib.pyplot as plt
from matplotlib.offsetbox import OffsetImage, AnnotationBbox
from matplotlib.animation import FuncAnimation

# Read CSV file
df = pd.read_csv('src/main/java/org/example/flights_data_NEW.csv')

# Create a directed graph
G = nx.from_pandas_edgelist(df, 'SOURCE', 'DESTINATION', ['WEIGHT'], create_using=nx.DiGraph())

# Read the path from the file
with open('src/main/java/org/example/output.txt', 'r') as file:
    shortest_path = file.readline().strip().split()

# Set up the graph layout
pos = nx.spring_layout(G)

# Create a full-screen figure
fig, ax = plt.subplots(figsize=(1200, 630))


# Load and plot the background image
# background_img_path = 'C:\\Users\\YASH SINGH\\Downloads\\Design_3.png'  # Provide the actual path to the background image
# background_img = plt.imread(background_img_path)
# ax.imshow(background_img, extent=[-1, 1, -1, 1], aspect='auto', zorder=-1)  # Adjust extent and aspect as needed

# Draw the graph
ax.set_title('Flight Graph with Shortest Path Highlighted')
ax.axis('off')  # Turn off axis labels

# Define path_edges before using it
path_edges = [(shortest_path[i], shortest_path[i + 1]) for i in range(len(shortest_path) - 1)]

# Convert OutEdgeView objects to sets
undirected_edges = set(G.edges) - set(G.edges).intersection(set(path_edges))

# Draw undirected edges
nx.draw_networkx_edges(G, pos, edgelist=undirected_edges, width=1.0, alpha=0.5, edge_color='gray', arrows=False)

# Draw directed edges for the highlighted path
edge_collection = nx.draw_networkx_edges(G, pos, edgelist=path_edges, width=2.0, edge_color='red', arrows=True)

# Draw nodes with full opacity
nx.draw_networkx_nodes(G, pos, node_size=700, node_color='skyblue', alpha=1.0)

# Draw labels
nx.draw_networkx_labels(G, pos, font_size=8, font_color='black', font_weight='bold')

# Add edge weights to the graph
edge_labels = nx.get_edge_attributes(G, 'WEIGHT')
nx.draw_networkx_edge_labels(G, pos, edge_labels=edge_labels, font_color='red', font_size=8)

# Load the airplane image
airplane_img_path = 'src/main/java/org/example/airplanelogo.png'  # Provide the actual path to the downloaded image
airplane_img = plt.imread(airplane_img_path)

# Plot the airplane on the first node of the path
start_node_position = pos[path_edges[0][0]]
imagebox = OffsetImage(airplane_img, zoom=0.1)
ab = AnnotationBbox(imagebox, start_node_position, frameon=False, pad=0)
ax.add_artist(ab)

# Function to update the position of the airplane in each frame
# Function to update the position of the airplane in each frame
def update(frame):
    if frame < len(path_edges):
        start_node, end_node = path_edges[frame]
        start_position = (pos[start_node][0], pos[start_node][1])
        end_position = (pos[end_node][0], pos[end_node][1])
        progress = frame / (len(path_edges) - 1)  # Normalize the progress to [0, 1]
        new_position = (start_position[0] + progress * (end_position[0] - start_position[0]),
                        start_position[1] + progress * (end_position[1] - start_position[1]))
        ab.xybox = new_position
    return ab,


# Create an animation
animation = FuncAnimation(fig, update, frames=len(path_edges), interval=500, repeat=False)

# Show the graph with animation
plt.show()
