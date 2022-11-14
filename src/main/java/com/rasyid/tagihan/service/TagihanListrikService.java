package com.rasyid.tagihan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rasyid.tagihan.entity.TagihanListrik;
import com.rasyid.tagihan.exception.TagihanNotFoundException;
import com.rasyid.tagihan.repository.TagihanListrikRepository;
import com.rasyid.tagihan.utils.ObjectUtils;


@Service
public class TagihanListrikService {
	
	@Autowired
	TagihanListrikRepository listrikRepository;
	
	public TagihanListrik create(TagihanListrik tagihanListrik){
		return listrikRepository.save(tagihanListrik);
	}

	public List<TagihanListrik> read(){
		return listrikRepository.findAll(Sort.by(Sort.Direction.ASC, "Id"));
	}
	
	public void delete(Long id) {
		TagihanListrik count = findById(id);
        if (count == null) {
            throw new TagihanNotFoundException("Could not find any users with ID " + id);
        }
        listrikRepository.deleteById(id);
	}

	public TagihanListrik update(Long id, TagihanListrik updateTL){
		
		TagihanListrik tagihanListrik = findById(id);
		updateTL.setId(tagihanListrik.getId());
		ObjectUtils.copyProperties(tagihanListrik, updateTL);
		return listrikRepository.save(updateTL);
		
	}
	
	public TagihanListrik findById (Long id) {
		Optional<TagihanListrik> result = listrikRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new TagihanNotFoundException("Could not find any users ID " + id);
	}
}
