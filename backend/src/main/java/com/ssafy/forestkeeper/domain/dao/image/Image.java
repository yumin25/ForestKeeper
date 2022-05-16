package com.ssafy.forestkeeper.domain.dao.image;

import com.ssafy.forestkeeper.domain.dao.BaseEntity;
import com.ssafy.forestkeeper.domain.dao.plogging.Plogging;
import com.ssafy.forestkeeper.domain.dao.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Image extends BaseEntity {

    @Column(name = "image_original_file_name")
    private String originalFileName;

    @Column(name = "image_saved_file_name")
    private String savedFileName;

    @Column(name = "image_is_resized")
    private boolean resize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plogging_id")
    private Plogging plogging;

    public void changeFileName(String originalFileName, String savedFileName) {
        this.originalFileName = originalFileName;
        this.savedFileName = savedFileName;
    }

    public void changeResize() {
        this.resize = true;
    }

}
