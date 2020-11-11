package mmbn.discord.bot.dto;

import java.util.Date;
import java.util.List;

public class ParticipantDetailResponseDto {

    private Long id;

    private Long tournament_id;

    private String name;

    private Long seed;

    private Boolean active;

    private Date created_at;

    private Date updated_at;

    private String invite_email;

    private String final_rank;

    private String misc;

    private String icon;

    private Boolean on_waiting_list;

    private Long invitation_id;

    private Long group_id;

    private Date checked_in_at;

    private Long ranked_member_id;

    private String challonge_username;

    private String challonge_email_address_verified;

    private Boolean removable;

    private Boolean participatable_or_invitation_attached;

    private Boolean confirm_remove;

    private Boolean invitation_pending;

    private String display_name_with_invitation_email_address;

    private String email_hash;

    private String username;

    private String attached_participatable_portrait_url;

    private Boolean can_check_in;

    private Boolean checked_in;

    private Boolean reactivatable;

    private Boolean check_in_open;

    private List<Long> group_player_ids;

    private Boolean has_irrelevant_seed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTournament_id() {
        return tournament_id;
    }

    public void setTournament_id(Long tournament_id) {
        this.tournament_id = tournament_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSeed() {
        return seed;
    }

    public void setSeed(Long seed) {
        this.seed = seed;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getInvite_email() {
        return invite_email;
    }

    public void setInvite_email(String invite_email) {
        this.invite_email = invite_email;
    }

    public String getFinal_rank() {
        return final_rank;
    }

    public void setFinal_rank(String final_rank) {
        this.final_rank = final_rank;
    }

    public String getMisc() {
        return misc;
    }

    public void setMisc(String misc) {
        this.misc = misc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getOn_waiting_list() {
        return on_waiting_list;
    }

    public void setOn_waiting_list(Boolean on_waiting_list) {
        this.on_waiting_list = on_waiting_list;
    }

    public Long getInvitation_id() {
        return invitation_id;
    }

    public void setInvitation_id(Long invitation_id) {
        this.invitation_id = invitation_id;
    }

    public Long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

    public Date getChecked_in_at() {
        return checked_in_at;
    }

    public void setChecked_in_at(Date checked_in_at) {
        this.checked_in_at = checked_in_at;
    }

    public Long getRanked_member_id() {
        return ranked_member_id;
    }

    public void setRanked_member_id(Long ranked_member_id) {
        this.ranked_member_id = ranked_member_id;
    }

    public String getChallonge_username() {
        return challonge_username;
    }

    public void setChallonge_username(String challonge_username) {
        this.challonge_username = challonge_username;
    }

    public String getChallonge_email_address_verified() {
        return challonge_email_address_verified;
    }

    public void setChallonge_email_address_verified(String challonge_email_address_verified) {
        this.challonge_email_address_verified = challonge_email_address_verified;
    }

    public Boolean getRemovable() {
        return removable;
    }

    public void setRemovable(Boolean removable) {
        this.removable = removable;
    }

    public Boolean getParticipatable_or_invitation_attached() {
        return participatable_or_invitation_attached;
    }

    public void setParticipatable_or_invitation_attached(Boolean participatable_or_invitation_attached) {
        this.participatable_or_invitation_attached = participatable_or_invitation_attached;
    }

    public Boolean getConfirm_remove() {
        return confirm_remove;
    }

    public void setConfirm_remove(Boolean confirm_remove) {
        this.confirm_remove = confirm_remove;
    }

    public Boolean getInvitation_pending() {
        return invitation_pending;
    }

    public void setInvitation_pending(Boolean invitation_pending) {
        this.invitation_pending = invitation_pending;
    }

    public String getDisplay_name_with_invitation_email_address() {
        return display_name_with_invitation_email_address;
    }

    public void setDisplay_name_with_invitation_email_address(String display_name_with_invitation_email_address) {
        this.display_name_with_invitation_email_address = display_name_with_invitation_email_address;
    }

    public String getEmail_hash() {
        return email_hash;
    }

    public void setEmail_hash(String email_hash) {
        this.email_hash = email_hash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAttached_participatable_portrait_url() {
        return attached_participatable_portrait_url;
    }

    public void setAttached_participatable_portrait_url(String attached_participatable_portrait_url) {
        this.attached_participatable_portrait_url = attached_participatable_portrait_url;
    }

    public Boolean getCan_check_in() {
        return can_check_in;
    }

    public void setCan_check_in(Boolean can_check_in) {
        this.can_check_in = can_check_in;
    }

    public Boolean getChecked_in() {
        return checked_in;
    }

    public void setChecked_in(Boolean checked_in) {
        this.checked_in = checked_in;
    }

    public Boolean getReactivatable() {
        return reactivatable;
    }

    public void setReactivatable(Boolean reactivatable) {
        this.reactivatable = reactivatable;
    }

    public Boolean getCheck_in_open() {
        return check_in_open;
    }

    public void setCheck_in_open(Boolean check_in_open) {
        this.check_in_open = check_in_open;
    }

    public List<Long> getGroup_player_ids() {
        return group_player_ids;
    }

    public void setGroup_player_ids(List<Long> group_player_ids) {
        this.group_player_ids = group_player_ids;
    }

    public Boolean getHas_irrelevant_seed() {
        return has_irrelevant_seed;
    }

    public void setHas_irrelevant_seed(Boolean has_irrelevant_seed) {
        this.has_irrelevant_seed = has_irrelevant_seed;
    }
}
