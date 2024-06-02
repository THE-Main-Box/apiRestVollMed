package med.voll.api.domain.dto.user;

import med.voll.api.domain.model.user.User;

public record UserDetailedDataDTO(Long id, String login) {
    public UserDetailedDataDTO(User user) {
        this(user.getId(), user.getLogin());
    }
}
