import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';

import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/factory">
      Factory
    </MenuItem>
    <MenuItem icon="asterisk" to="/business-unit">
      Business Unit
    </MenuItem>
    <MenuItem icon="asterisk" to="/production-line">
      Production Line
    </MenuItem>
    <MenuItem icon="asterisk" to="/production-line-group">
      Production Line Group
    </MenuItem>
    <MenuItem icon="asterisk" to="/metric">
      Metric
    </MenuItem>
    <MenuItem icon="asterisk" to="/metric-data">
      Metric Data
    </MenuItem>
    <MenuItem icon="asterisk" to="/company-user">
      Company User
    </MenuItem>
    <MenuItem icon="asterisk" to="/user-profile">
      User Profile
    </MenuItem>
    <MenuItem icon="asterisk" to="/location-data">
      Location Data
    </MenuItem>
    <MenuItem icon="asterisk" to="/things">
      Things
    </MenuItem>
    <MenuItem icon="asterisk" to="/state">
      State
    </MenuItem>
    <MenuItem icon="asterisk" to="/status">
      Status
    </MenuItem>
    <MenuItem icon="asterisk" to="/product-data">
      Product Data
    </MenuItem>
    <MenuItem icon="asterisk" to="/production-plan">
      Production Plan
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
