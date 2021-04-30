from mpl_toolkits import mplot3d
import matplotlib.pyplot as plt
import numpy as np
file1 = open('data.txt', 'r')
Lines = file1.readlines()
p=[]
w=[]
t=[]
for line in Lines:
    w.append(int(line.split()[0]))
    p.append(float(line.split()[1]))
    t.append(int(line.split()[2]))
P = np.array(p)
W = np.array(w)
T = np.array(t)
fig = plt.figure()
ax = plt.axes(projection='3d')
ax.scatter(P, W, T,
           linewidths=1, alpha=.7,
           edgecolor='k',
           s = 100,
           c=T)
ax.set_xlabel('Probability for ON')
ax.set_ylabel('Width')
ax.set_zlabel('Time')
#ax.scatter(P, W, T,cmap='hsv', edgecolor='none')
plt.show()
