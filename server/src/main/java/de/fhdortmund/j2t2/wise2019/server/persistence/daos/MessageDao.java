package de.fhdortmund.j2t2.wise2019.server.persistence.daos;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.SimpleMessage;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.GameState;
import de.fhdortmund.j2t2.wise2019.server.persistence.entities.*;

import javax.ejb.Stateful;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@Stateful
public class MessageDao extends AbstractDao{


    public AbstractMessageEntity persist(SimpleMessage msg, GameStateEntity gameState) {
        AbstractMessageEntity _message;
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        if(msg instanceof Message){
            Message _msg = (Message) msg;
            ComplexMessageEntity message = new ComplexMessageEntity();
            message.setId(_msg.getId());
            message.setGameClazz(gameState.getGameClazz());
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

    @SuppressWarnings({"unchecked"})
    public SimpleMessage deserialize(AbstractMessageEntity messageEntity, GameState gameState) {
        if(messageEntity instanceof SimpleMessageEntity){
            SimpleMessageEntity simpleMessageEntity = (SimpleMessageEntity)  messageEntity;
            return simpleMessageEntity::getText;
        } else if (messageEntity instanceof ComplexMessageEntity){
            ComplexMessageEntity cme = (ComplexMessageEntity) messageEntity;
            try {
                Class<? extends Game> gameClass = (Class<? extends Game>) Class.forName(cme.getGameClazz());
                Game game = gameClass.newInstance();
                game.setGameState(gameState);
                game.updateGameModel();
                return game.getMessageById(cme.getId());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException(messageEntity.getClass().getName() +"not supported");
        }
    }
}
