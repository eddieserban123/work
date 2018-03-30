from main.network import Network


net = Network([3,2,3])

traininig_data = list(zip(range(1,10),range(10,1,-1)))
epochs = 5
mini_batch_size=2
eta=0.01
test_data=[]
net.SGD(traininig_data, epochs, mini_batch_size, eta, test_data)



