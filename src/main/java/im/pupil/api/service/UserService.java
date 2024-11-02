package im.pupil.api.service;

import im.pupil.api.exception.user.UserNotFoundException;
import im.pupil.api.model.User;
import im.pupil.api.repository.UserRepository;
import im.pupil.api.security.user.details.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImpl(userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("No user with email: " + username)
        )
        );
    }

    public UserDetailsService userDetailsService() {
        return this;
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
