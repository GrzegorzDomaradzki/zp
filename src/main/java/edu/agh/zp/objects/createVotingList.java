package edu.agh.zp.objects;

import edu.agh.zp.repositories.VotingRepository;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public class createVotingList {

    public static void run(ModelAndView modelAndView, Collection<VotingEntity.TypeOfVoting> type, VotingRepository votingRepository)
    {
        LocalDate now = LocalDate.now();
        Date nowSql = java.sql.Date.valueOf(now);
        List<VotingEntity> voting = votingRepository.findByVotingDateBetweenAndVotingTypeIsInOrderByVotingDateAscOpenVotingAsc(nowSql, java.sql.Date.valueOf(now.plusDays(1)), type);
        TableCreation(modelAndView, nowSql, voting);
    }

    private static void TableCreation(ModelAndView modelAndView, Date nowSql, List<VotingEntity> voting) {
        boolean[] nums = new boolean[voting.size()];
        Time time = Time.valueOf(LocalTime.now());
        if (voting.isEmpty()) modelAndView.addObject("Sign", null);
        else {
            for (int i = 0; i < voting.size(); i++) {
                VotingEntity vote = voting.get(i);
                if (vote.getVotingDate().equals(nowSql) && vote.getOpenVoting().before(time) && vote.getCloseVoting().after(time))
                    nums[i] = true;
            }
            modelAndView.addObject("Sign", nums);
        }
        modelAndView.addObject("VotingList", voting);
    }

    public static void past(ModelAndView modelAndView, VotingEntity.TypeOfVoting type, VotingRepository votingRepository)
    {
        LocalDate now = LocalDate.now();
        Date nowSql = java.sql.Date.valueOf(now);
        Time time = java.sql.Time.valueOf(LocalTime.now());
        List<VotingEntity> voting = votingRepository.findByVotingDateBeforeAndVotingTypeOrderByVotingDateDescOpenVotingDesc(nowSql, type);
        List<VotingEntity> voting2 =votingRepository.findByVotingDateAndCloseVotingBeforeAndVotingTypeOrderByVotingDateDescOpenVotingDesc(nowSql,time,type);
        voting2.addAll(voting);
        modelAndView.addObject("VotingList", voting2);
    }

    public static void future(ModelAndView modelAndView, Collection<VotingEntity.TypeOfVoting> type, VotingRepository votingRepository)
    {
        LocalDate now = LocalDate.now();
        Date nowSql = java.sql.Date.valueOf(now);
        List<VotingEntity> voting = votingRepository.findByVotingDateAfterAndVotingTypeIsInOrderByVotingDateAscOpenVotingAsc(java.sql.Date.valueOf(now.minusDays(1)), type);
        TableCreation(modelAndView, nowSql, voting);
    }
}
