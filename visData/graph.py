import numpy as np 
import matplotlib.pyplot as plt 

"""
a. Fije Carga y genere: # threads vs. tiempo de creación de la llave (3 cargas diferentes = 3 gráficas, 3
tamaños de pool -1, 2 y 8-: 3 conjuntos de puntos por gráfica)
b. Fije Carga y genere: # threads vs. tiempo de actualización (mismo número de gráficas)
c. Fije Carga y genere: # threads vs. # de transacciones perdidas (mismo número de gráficas)
d. Fije Carga y genere # threads vs. porcentaje de uso de la CPU en el servidor (mismo número de gráficas)

"""

#a 

threads =  [1,2,8]
cargas = [80,200,400]
cont = 400
for carga in cargas: 
    
    tiempoLlave = []
    thread = []

    for i in range(10):
        i += 1
        for thr in threads:

            data = np.loadtxt("./AllData/Cliente/data"+str(i) + "/"+str(thr)+"-"+str(carga)+".dat")
            tiempoLlave.extend(data[:,1].tolist())
            for j in range(len(data[:,1])):
                thread.append(thr)



    #plot
    # print(tiempoLlave)
    # print(thread)
    # plt.scatter(thread,tiempoLlave, label = "carga = "+str(carga),s= cont, alpha = 1 - cont/500 )
    plt.scatter(thread,tiempoLlave, label = "carga = "+str(carga) )
    cont *= 0.2


    # plt.legend(borderpad = 1, labelspacing = 2)
    plt.title("Threads vs tiempo de creacion de llaves, carga = " +str(carga))
    plt.savefig("tLlaves"+str(carga)+".png")
    plt.close()
    

#b

threads =  [1,2,8]
cargas = [80,200,400]
cont = 400
for carga in cargas: 
    
    tiempoLlave = []
    thread = []

    for i in range(10):
        i += 1
        for thr in threads:

            data = np.loadtxt("./AllData/Cliente/data"+str(i) + "/"+str(thr)+"-"+str(carga)+".dat")
            tiempoLlave.extend(data[:,2].tolist())
            for j in range(len(data[:,2])):
                thread.append(thr)



    #plot
    # print(tiempoLlave)
    # print(thread)
    # plt.scatter(thread,tiempoLlave, label = "carga = "+str(carga),s= cont, alpha = 1 - cont/500 )
    plt.scatter(thread,tiempoLlave, label = "carga = "+str(carga))
    cont *= 0.2


    # plt.legend(borderpad = 1, labelspacing = 2)
    plt.title("Threads vs tiempo de actualizacion, carga = "+str(carga))
    plt.savefig("tAct"+str(carga)+".png")
    plt.close()

#c


threads =  [1,2,8]
cargas = [80,200,400]
cont = 400
for carga in cargas: 
    
    tiempoLlave = []
    thread = []

    for i in range(10):
        i += 1
        for thr in threads:

            data = np.loadtxt("./AllData/Cliente/data"+str(i) + "/"+str(thr)+"-"+str(carga)+".dat")
            # tiempoLlave.extend(data[:,3].tolist())
            # for j in range(len(data[:,3])):
            # thread.append(thr)  
            thread.append(thr)
            tiempoLlave.append(carga - len(data[:,3]))



    #plot
    # print(tiempoLlave)
    # print(thread)
    # plt.scatter(thread,tiempoLlave, label = "carga = "+str(carga),s= cont, alpha = 1 - cont/500 )
    plt.scatter(thread,tiempoLlave, label = "carga = "+str(carga))
    cont *= 0.2


    # plt.legend(borderpad = 1, labelspacing = 2)
    plt.title("Threads vs transacciones perdidas, carga = "+str(carga))
    plt.savefig("perdidas"+str(carga)+".png")
    plt.close()


#d


threads =  [1,2,8]
cargas = [80,200,400]
cont = 400
for carga in cargas: 
    
    tiempoLlave = []
    thread = []

    for i in range(10):
        i += 1
        for thr in threads:

            data = np.loadtxt("./AllData/Cliente/data"+str(i) + "/"+str(thr)+"-"+str(carga)+".dat")
            tiempoLlave.extend(data[:,3].tolist())
            for j in range(len(data[:,3])):
                thread.append(thr)



    #plot
    # print(tiempoLlave)
    # print(thread)
    # plt.scatter(thread,tiempoLlave, label = "carga = "+str(carga),s= cont, alpha = 1 - cont/500 )
    plt.scatter(thread,tiempoLlave, label = "carga = "+str(carga))
    cont *= 0.2


    # plt.legend(borderpad = 1, labelspacing = 2)
    plt.title("Threads vs uso de CPU, carga = "+str(carga))
    plt.savefig("tCpu"+str(carga)+".png")
    plt.close()