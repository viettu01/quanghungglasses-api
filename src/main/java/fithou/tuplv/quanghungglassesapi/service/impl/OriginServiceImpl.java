package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.request.OriginRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.OriginResponse;
import fithou.tuplv.quanghungglassesapi.entity.Origin;
import fithou.tuplv.quanghungglassesapi.mapper.OriginMapper;
import fithou.tuplv.quanghungglassesapi.repository.OriginRepository;
import fithou.tuplv.quanghungglassesapi.service.OriginService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.ERROR_ORIGIN_ALREADY_EXISTS;
import static fithou.tuplv.quanghungglassesapi.utils.Constants.ERROR_ORIGIN_NOT_FOUND;

@Service
@Transactional
@AllArgsConstructor
public class OriginServiceImpl implements OriginService {
    final OriginRepository originRepository;
    final OriginMapper originMapper;

    @Override
    public List<OriginResponse> findAll() {
        return originRepository.findAll().stream().map(originMapper::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public OriginResponse findById(Long id) {
        return originMapper.convertToResponse(originRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_ORIGIN_NOT_FOUND)));
    }

    @Override
    public OriginResponse create(OriginRequest originRequest) {
        if (originRepository.existsByName(originRequest.getName()))
            throw new RuntimeException(ERROR_ORIGIN_ALREADY_EXISTS);
        return originMapper.convertToResponse(originRepository.save(originMapper.convertToEntity(originRequest)));
    }

    @Override
    public OriginResponse update(OriginRequest originRequest) {
        Origin origin = originRepository.findById(originRequest.getId()).orElseThrow(() -> new RuntimeException(ERROR_ORIGIN_NOT_FOUND));
        if (!origin.getName().equalsIgnoreCase(originRequest.getName()) && originRepository.existsByName(originRequest.getName()))
            throw new RuntimeException(ERROR_ORIGIN_ALREADY_EXISTS);
        return originMapper.convertToResponse(originRepository.save(originMapper.convertToEntity(originRequest)));
    }

    @Override
    public void deleteByIds(Long[] ids) {
        for (Long id : ids) {
            try {
                originRepository.deleteById(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean existsByName(String name) {
        return originRepository.existsByName(name);
    }
}
