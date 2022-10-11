package com.alex.eyk.telegram.data.entity.recent

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecentDirectionRepository : JpaRepository<RecentDirection, Long>
