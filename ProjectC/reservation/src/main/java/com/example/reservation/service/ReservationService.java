package com.example.reservation.service;

import com.example.reservation.domain.ReservationUserComment;
import com.example.reservation.domain.ReservationUserCommentImage;
import com.example.reservation.dto.ReservationUserCommentDto;
import com.example.reservation.repository.ReservationUserCommentImageRepository;
import com.example.reservation.repository.ReservationUserCommentRepository;
import com.example.reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class ReservationService {
    private final ReservationUserCommentRepository reservationUserCommentRepository;
    private final UserRepository userRepository;
    private final ReservationUserCommentImageRepository reservationUserCommentImageRepository;

    public static final int LIMIT = 5;

    @Autowired
    public ReservationService(ReservationUserCommentRepository reservationUserCommentRepository, UserRepository userRepository, ReservationUserCommentImageRepository reservationUserCommentImageRepository) {
        this.reservationUserCommentRepository = reservationUserCommentRepository;
        this.userRepository = userRepository;
        this.reservationUserCommentImageRepository = reservationUserCommentImageRepository;
    }

    public int getAvgScoreByProductId(long productId) {
        List<Double> scores = reservationUserCommentRepository.getScoreByProductId(productId);
        double sum = 0;
        for(double score : scores) {
            sum += score;
        }
        return (int) (sum / scores.size());
    }

    public List<ReservationUserCommentDto.Response> getCommentsByProductId(long productId, int start) {
        List<ReservationUserComment> comments = reservationUserCommentRepository.findByProductId(productId, start, start + LIMIT);
        List<ReservationUserCommentDto.Response> result = new LinkedList<>();

        for(ReservationUserComment comment : comments) {
            String email = userRepository.getEmailById(comment.getUser_id());
            List<ReservationUserCommentImage> commentImages = reservationUserCommentImageRepository.findByCommentId(comment.getId());
            result.add(new ReservationUserCommentDto.Response(comment.getId(), comment.getProduct_id(), comment.getReservation_info_id(), comment.getScore(), email, comment.getComment(), comment.getCreate_date(), comment.getModify_date(), commentImages));
        }
        return result;
    }

    public int getTotalCount(long productId) {
        return reservationUserCommentRepository.getTotalCount(productId);
    }
}
