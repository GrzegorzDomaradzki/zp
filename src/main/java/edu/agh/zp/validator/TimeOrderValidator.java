package edu.agh.zp.validator;

import edu.agh.zp.objects.VotingEntity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeOrderValidator implements ConstraintValidator<TimeOrder, VotingEntity > {
	@Override
	public boolean isValid( VotingEntity voting, ConstraintValidatorContext constraintValidatorContext ) {
		if (voting.getVotingType() == VotingEntity.TypeOfVoting.PREZYDENT ||voting.getVotingType() == VotingEntity.TypeOfVoting.REFERENDUM) return true;
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");

		Time timeValueOpen;
		Time timeValueClose;
		if(voting.getOpen() != null ) {
			try {
				timeValueOpen = new Time(formatter.parse(voting.getOpen()).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
				return false;
			}
			try {
				timeValueClose = new Time(formatter.parse(voting.getClose()).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
				return false;
			}
		}else{
			timeValueOpen = voting.getOpenVoting();
			timeValueClose = voting.getCloseVoting();
		}

		return timeValueClose.after( timeValueOpen );
	}
}
