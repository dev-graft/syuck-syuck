package org.devgraft.ohlot.service;

import org.devgraft.ohlot.domain.GoodWord;
import org.devgraft.ohlot.domain.GoodWordRepository;
import org.devgraft.ohlot.provider.UUIDProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GoodWordServiceImpl implements GoodWordService {
    private final UUIDProvider uuidProvider;
    private final GoodWordRepository goodWordRepository;

    @Override
    public GoodWordAddResponse addGoodWord(GoodWordAddRequest request) {
        UUID uuid = uuidProvider.randomUUID();
        if (goodWordRepository.existsById(uuid)) {
            throw new RuntimeException();
        }

        GoodWord goodWord = GoodWord.create(uuid, request.getContent());
        goodWordRepository.save(goodWord);

        return new GoodWordAddResponse(uuid.toString(), request.getContent());
    }

    @Override
    public List<GoodWordGetResponse> getGoodWords() {
        List<GoodWordGetResponse> responses = new ArrayList<>();

        return goodWordRepository.findAll()
                .stream()
                .map(GoodWordGetResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public void updateGoodWord(GoodWordUpdateRequest request) {
        GoodWord goodWord = goodWordRepository.findById(request.getId())
                .orElseThrow(RuntimeException::new);

        goodWord.update(request.getContent());
        goodWordRepository.save(goodWord);
    }

    @Override
    public void deleteGoodWord(UUID id) {
        goodWordRepository.deleteById(id);
    }
}
