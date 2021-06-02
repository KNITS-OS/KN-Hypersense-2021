import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './company-user.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICompanyUserDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CompanyUserDetail = (props: ICompanyUserDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { companyUserEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="companyUserDetailsHeading">CompanyUser</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{companyUserEntity.id}</dd>
          <dt>
            <span id="usersUuid">Users Uuid</span>
          </dt>
          <dd>{companyUserEntity.usersUuid}</dd>
          <dt>User Profile</dt>
          <dd>{companyUserEntity.userProfile ? companyUserEntity.userProfile.id : ''}</dd>
          <dt>Business Unit</dt>
          <dd>{companyUserEntity.businessUnit ? companyUserEntity.businessUnit.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/company-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company-user/${companyUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ companyUser }: IRootState) => ({
  companyUserEntity: companyUser.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyUserDetail);
