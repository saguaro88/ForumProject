package com.javaee.projectFroum.projectForum;

import com.javaee.projectFroum.projectForum.models.Topic;
import com.javaee.projectFroum.projectForum.models.User;
import com.javaee.projectFroum.projectForum.repositories.TopicRepository;
import com.javaee.projectFroum.projectForum.repositories.UserRepository;
import com.javaee.projectFroum.projectForum.services.UserServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTests {
    private static User testUser;
    private static Topic topic;

    @Mock
    UserRepository userRepository;
    @Mock
    TopicRepository topicRepository;
    @InjectMocks
    UserServiceImpl userService;

    @BeforeClass
    public static void init() throws ParseException {
      testUser = new User();
      testUser.setId(1L);
      testUser.setPostCounter(20L);
      testUser.setPassword("testPassword");
      testUser.setEmail("projectforumpb@gmail.com");
      testUser.setCreatedAt(new SimpleDateFormat("dd/MM/yyyy").parse("13/06/2020"));
      testUser.setFollowedTopics(null);
      testUser.setUsername("testUser");
        topic = new Topic();
        topic.setId(0L);
        topic.setTitle("Test");
        topic.setUser(testUser);
        topic.setCreationDate(new SimpleDateFormat("dd/MM/yyyy").parse("13/06/2020"));
        topic.setPosts(null);
    }

    @Test
    public void findUserById1(){
        assert !userService.findUserById(0L).isPresent();
    }

    @Test
    public void findUserById2(){
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(testUser));
        assert userService.findUserById(1L).isPresent();
    }
    @Test
    public void findUserById3(){
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(testUser));
        User user = userService.findUserById(1L).get();
        assert user.equals(testUser);
    }


}
