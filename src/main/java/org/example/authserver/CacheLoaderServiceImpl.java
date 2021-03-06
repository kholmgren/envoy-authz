package org.example.authserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Set;
import java.util.regex.Pattern;

@Slf4j
@Service
public class CacheLoaderServiceImpl implements CacheLoaderService {

    private final CacheService cacheService;
    private final AclRepository aclRepository;

    public CacheLoaderServiceImpl(CacheService cacheService, AclRepository aclRepository) {
        this.cacheService = cacheService;
        this.aclRepository = aclRepository;
    }

    @Override
    public void subscribe() {
        Flux<String> flux = aclRepository.subscribe();
        flux
            .doOnNext(acl -> loadData())
            .subscribeOn(Schedulers.parallel())
            .subscribe();

        loadData();
    }

    protected void loadData() {
        log.info("loadData");
        Set<Acl> acls = aclRepository.findAll();
        for (Acl acl : acls){
            Pattern pattern = Pattern.compile(acl.getResourceRegex());
            cacheService.putToPatternCache(pattern, acl);
        }
    }

}
