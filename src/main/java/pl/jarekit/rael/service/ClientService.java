package pl.jarekit.rael.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jarekit.rael.logs.Level;
import pl.jarekit.rael.logs.LogUtils;
import pl.jarekit.rael.model.Client;
import pl.jarekit.rael.repo.ClientRepo;

import java.util.Optional;

@Service
public class ClientService {

    private ClientRepo clientRepo;
    private LoginService loginService;

    @Autowired
    public ClientService(ClientRepo clientRepo, LoginService loginService) {
        this.clientRepo = clientRepo;
        this.loginService = loginService;
    }

    public Client saveClient(Client client){
        client.setStatus("active");
        client.setUser(loginService.getUser());
        LogUtils.saveLogStatic("Added client: " + client , Level.INFO);
        return clientRepo.save(client);
    }

    public Iterable<Client> saveClients(Iterable<Client>  clients){
        LogUtils.saveLogStatic("Added list of clients: " + clients , Level.INFO);
        return clientRepo.saveAll(clients);
    }

    public Iterable<Client> getClients(){
        LogUtils.saveLogStatic("Loaded all clients" , Level.INFO);
        return clientRepo.findAllByUser(loginService.getUser());
    }

    public Client getClientById(long id) {
        Optional<Client> loadedClient = clientRepo.findById(id);
        if (loadedClient.isPresent()) {
            LogUtils.saveLogStatic("Loaded client: " + loadedClient, Level.INFO);
            return loadedClient.get();
        } else {
            LogUtils.saveLogStatic("Failed Loaded all by id: " + id, Level.WARNING);
            return loadedClient.orElse(null);
        }
    }

    public Client updateClient(Client client){

        if (clientRepo.existsById(client.getId())) {
            Client clientToEdit = clientRepo.findById((client.getId())).get();
            String log = "Edited client: from: " + clientToEdit;

            clientToEdit.setCompanyName(client.getCompanyName());
            clientToEdit.setCompanyNumber(client.getCompanyNumber());
            clientToEdit.setPersonalName(client.getPersonalName());
            clientToEdit.setPersonalSurname(client.getPersonalSurname());
            clientToEdit.setPersonalNumber(client.getPersonalNumber());
            clientToEdit.setPersonalName(client.getPersonalName());
            clientToEdit.setAddress(client.getAddress());
            clientToEdit.setStatus(client.getStatus());

            log = log + ", to: " + clientToEdit;
            LogUtils.saveLogStatic(log, Level.INFO);
            return clientRepo.save(clientToEdit);
        } else{
            LogUtils.saveLogStatic( "Failed: Edited client, can't find client id: " + client.getId() +
                    ", so created new Client: " + client, Level.WARNING);
            return clientRepo.save(client);
        }
    }


    public String deleteClient(long id){
        Optional<Client> clientToDelete = clientRepo.findById(id);
        if (clientToDelete.isPresent()){
            clientRepo.deleteById(id);
            LogUtils.saveLogStatic("Deleted client: " + clientToDelete, Level.INFO);
            return "Deleted client: " + clientToDelete;
        } else {
            LogUtils.saveLogStatic("Failed Deleted client by id: " + id, Level.WARNING);
            return "Failed Deleted client by id: " + id;
        }
    }

    public boolean existById(long id){
        return clientRepo.existsById(id);
    }

    public Iterable<Client> getClientsForAdmin(){
        LogUtils.saveLogStatic("Loaded all clients for admin panel" , Level.WARNING);
        return clientRepo.findAll();
    }
}
