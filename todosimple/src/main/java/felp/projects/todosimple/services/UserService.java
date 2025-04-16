package felp.projects.todosimple.services;

import felp.projects.todosimple.models.User;
import felp.projects.todosimple.models.enums.ProfileEnum;
import felp.projects.todosimple.repositories.UserRepository;
import felp.projects.todosimple.services.exceptions.DataBindingViolationException;
import felp.projects.todosimple.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
     private BCryptPasswordEncoder BCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User findById(Integer id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Id: " + id + ", Usuário: " + User.class.getName()));
    }

    @Transactional
    public User create(User obj) {
        obj.setId(null);
        obj.setPassword(this.BCryptPasswordEncoder.encode(obj.getPassword()));
        obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User update(User obj) {
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        newObj.setPassword(this.BCryptPasswordEncoder.encode(obj.getPassword()));
        return this.userRepository.save(newObj);
    }

    public void delete(Integer id) {
        findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
        }
    }
}
