package de.fhdortmund.j2t2.wise2019.server.persistence.daos;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.SimpleMessage;
import de.fhdortmund.j2t2.wise2019.server.persistence.entities.*;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

@Named
@Stateful
public class MessageDao extends AbstractDao{


    public AbstractMessageEntity persist(SimpleMessage msg) {
        AbstractMessageEntity _message;
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        if(msg instanceof Message){
            Message _msg = (Message) msg;
            ComplexMessageEntity message = new ComplexMessageEntity();
            message.setId(_msg.getId());
            message.setDelay(_msg.getDelay());
            message.setProbably(_msg.getProbably());
            message.setRoot(_msg.isRoot());
            message.setText(_msg.getText());
            em.persist(message);
            List<AnswerEntity> answerEntities = this.persist(_msg.getAnswers(), message);
            message.setAnswers(answerEntities);
            _message = message;
        } else {
            SimpleMessageEntity message = new SimpleMessageEntity();
            message.setText(msg.getText());
            _message = message;
        }
        em.getTransaction().commit();
        em.close();
        return _message;
    }

    public List<AnswerEntity> persist(List<? extends Answer> answers, ComplexMessageEntity message){
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        List<AnswerEntity> answerEntities = new ArrayList<>(answers.size());
        for(Answer answer : answers){
            AnswerEntity answerEntity = new AnswerEntity();
            answerEntity.setId(answer.getId());
            answerEntity.setParent(message);
            answerEntity.setText(answer.getText());
            answerEntity.setTargetIds(persist(answer.getTargetIds(), answerEntity));
            em.persist(answerEntity);
            answerEntities.add(answerEntity);
        }
        em.getTransaction().commit();
        em.close();
        return answerEntities;
    }

    public List<TargetId> persist(List<String> targetIds, AnswerEntity answerEntity){
        List<TargetId> targetIdList = new ArrayList<>(targetIds.size());
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        for(String targetId : targetIds){
            TargetId target = new TargetId();
            target.setTargetId(targetId);
            target.setAnswer(answerEntity);
            targetIdList.add(target);
        }
        em.getTransaction().commit();
        em.close();
        return targetIdList;
    }
}
