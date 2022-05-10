package com.alex.eyk.telegram.model.entity.recent

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecentDirectionRepository : JpaRepository<RecentDirection, Long>
