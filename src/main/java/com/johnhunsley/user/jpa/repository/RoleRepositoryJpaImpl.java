package com.johnhunsley.user.jpa.repository;

import com.johnhunsley.user.domain.Role;
import com.johnhunsley.user.jpa.domain.RoleJpaImpl;
import com.johnhunsley.user.repository.RoleRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 * </p>
 *
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 01/12/2016
 *         Time : 19:42
 */
@Repository("roleRepository")
@Profile("jpa")
public interface RoleRepositoryJpaImpl extends RoleRepository, JpaRepository<RoleJpaImpl, Long> {

    @Override
    List<RoleJpaImpl> findAll();

    @Override
    void save(Role role);

    @Override
    Role findById(Integer integer);
}
